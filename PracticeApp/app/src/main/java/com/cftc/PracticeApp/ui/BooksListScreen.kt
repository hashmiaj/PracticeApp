package com.cftc.PracticeApp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.Book
import com.cftc.PracticeApp.viewmodel.LibraryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(onBookClick: (id: String) -> Unit) {
    val vm: LibraryViewModel = viewModel()
    val state: LibraryUiState by vm.state.collectAsState()


    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val showFab by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 200
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Library") },
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = showFab) {
                FloatingActionButton(
                    onClick = { scope.launch { listState.animateScrollToItem(0) }}
                ) {
                    Icon(Icons.Filled.ArrowUpward, contentDescription = "Scroll to top")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 12.dp)
                ) {
                    OutlinedTextField(
                        value = state.inputQuery,
                        onValueChange = vm::onQueryChanged,
                        placeholder = { Text("Search books...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            when {
                state.isLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                state.showEmpty -> {
                    item { EmptyList(state.inputQuery) }
                }

                else -> {
                    items(
                        count = state.visibleList.size,
                        key = { index -> state.visibleList[index].id },
                        contentType = { _ -> "books" }
                    ) { index ->
                        BookItem(state.visibleList[index], onBookClick)
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(item: Book, onBookClick: (id: String) -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .padding(16.dp)
            .height(100.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onBookClick(item.id) }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookImage(item.imageUrl)
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = item.title, style = MaterialTheme.typography.titleLarge)
                Text(text = item.author, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "chevron"
            )
        }
    }
}

@Composable
fun BookImage(imageUrl: String?) {
    if (imageUrl == null) {
        Icon(
            imageVector = Icons.Filled.LibraryBooks,
            contentDescription = "book placeholder image",
            modifier = Modifier.size(80.dp),
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "book image",
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun EmptyList(inputQuery: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Book,
            contentDescription = "book placeholder image",
            modifier = Modifier.size(72.dp),
        )
        Text(text = "No books found", style = MaterialTheme.typography.titleLarge)
        Text(
            text = "No books match \"$inputQuery\". Try a different search",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}