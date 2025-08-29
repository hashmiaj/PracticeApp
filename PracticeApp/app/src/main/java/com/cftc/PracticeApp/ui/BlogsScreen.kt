package com.cftc.PracticeApp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.BlogPost
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun BlogsScreen(vm: BlogViewModel = viewModel()) {
    val state by vm.state.collectAsState()
    val snackbar = remember { SnackbarHostState() }

    // Only once for the screen's lifetime
    LaunchedEffect(Unit) { vm.loadFirstPage() }

    // One-shot effects (won't replay on rotate)
    LaunchedEffect(Unit) {
        vm.effects.collect { eff ->
            when (eff) {
                is BlogsUiEffect.ShowMessage -> snackbar.showSnackbar(eff.message)
            }
        }
    }

    // Stable, saveable list state (prevents jump-to-top)
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbar) }) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (val s = state) {
                BlogsUiState.Initial,
                BlogsUiState.Loading -> LoadingView()

                is BlogsUiState.Error -> ErrorView(s.message, onRetry = vm::retryInitial)

                is BlogsUiState.Content -> {
                    // Keep latest s inside collectors without restarting them
                    val latest by rememberUpdatedState(s)

                    // Load-more trigger that won't spam: snapshot visible range and total
                    LaunchedEffect(listState) {
                        snapshotFlow {
                            val last = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
                            val total = listState.layoutInfo.totalItemsCount
                            last to total
                        }
                            .distinctUntilChanged()
                            .collect { (last, total) ->
                                // within last 3 items & allowed & not already appending
                                if (latest.canLoadMore && !latest.isAppending && last >= total - 3) {
                                    vm.loadNextPage()
                                }
                            }
                    }

                    BlogsList(
                        items = latest.items,
                        isAppending = latest.isAppending,
                        appendError = latest.appendError,
                        canLoadMore = latest.canLoadMore,
                        listState = listState,
                        onRetryAppend = vm::loadNextPage
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingView() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
private fun BlogsList(
    items: List<BlogPost>,
    isAppending: Boolean,
    appendError: String?,
    canLoadMore: Boolean,
    listState: LazyListState,
    onRetryAppend: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id },
            contentType = { _ -> "posts" },
        ) { index ->
            val post = items[index]
            ListItem(
                headlineContent = { Text(post.title) },
                supportingContent = { Text(post.description) }
            )
            HorizontalDivider()
        }

        // Stable footer (key + contentType) prevents layout thrash
        item(key = "append-footer", contentType = "footer") {
            when {
                isAppending -> Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }

                appendError != null -> Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Failed to load more")
                    TextButton(onClick = onRetryAppend) { Text("Retry") }
                }

                // Optional: render nothing but keep a stable footer node
                canLoadMore -> Spacer(Modifier.height(1.dp))
                else -> Spacer(Modifier.height(0.dp))
            }
        }
    }
}

