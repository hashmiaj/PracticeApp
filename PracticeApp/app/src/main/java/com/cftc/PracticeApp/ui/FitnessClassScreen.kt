package com.cftc.PracticeApp.ui

import android.R
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.material3.pulltorefresh.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.ClassCategory
import com.cftc.PracticeApp.model.FitnessClass
import com.cftc.PracticeApp.uistate.FitnessClassUiState
import com.cftc.PracticeApp.viewmodel.FitnessClassViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessClassScreen() {
    val vm: FitnessClassViewModel = viewModel()
    val state: FitnessClassUiState by vm.state.collectAsState()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var confirmId by rememberSaveable { mutableStateOf<String?>(null) }

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            vm.refresh()
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CityFit Classes",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbar) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            ClassesFilterChips(state.selected, vm::selectCategory)

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    ShowEmptyScreen()
                }

                else -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val groupedByDate: Map<String, List<FitnessClass>> =
                                state.visibleList.groupBy { it.date }
                            groupedByDate.forEach { (date, classes) ->
                                stickyHeader {
                                    Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.CalendarMonth,
                                                contentDescription = "cal icon"
                                            )
                                            val date = LocalDate.parse(date)
                                            val formattedDate =
                                                DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US)
                                            Text(
                                                text = date.format(formattedDate),
                                                style = MaterialTheme.typography.titleLarge,
                                            )
                                        }
                                        HorizontalDivider(
                                            Modifier.padding(
                                                horizontal = 16.dp,
                                            )
                                        )
                                    }
                                }
                                items(
                                    count = classes.size,
                                    key = { index -> classes[index].id }) { i ->
                                    ClassCard(
                                        classes[i],
                                        onToggleJoined = { confirmId = classes[i].id })
                                }
                            }
                        }
                        val showIndicator =
                            pullToRefreshState.isRefreshing || pullToRefreshState.progress > 0f
                        if (showIndicator) {
                            PullToRefreshContainer(
                                state = pullToRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
        val pending = state.visibleList.firstOrNull() { it.id == confirmId }
        if (pending != null) {
            AlertDialog(
                onDismissRequest = { confirmId = null },
                title = { Text("Join class?") },
                text = { Text("Join ${pending.title} with ${pending.coach}?") },
                confirmButton = {
                    TextButton(onClick = {
                        val id = pending.id
                        confirmId = null
                        vm.toggleJoinClass(id)
                        scope.launch {
                            val res = snackbar.showSnackbar("Joined class", actionLabel = "UNDO")
                            if (res == SnackbarResult.ActionPerformed) vm.toggleJoinClass(id)
                        }
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { confirmId = null }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
fun ShowEmptyScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.SportsGymnastics,
            contentDescription = "empty",
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "No classes available, check back later!",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun ClassCard(fitnessClass: FitnessClass, onToggleJoined: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClassImage(fitnessClass.imageUrl)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = fitnessClass.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = fitnessClass.startTime + "-" + fitnessClass.endTime,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = fitnessClass.coach,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Button(
                onClick = onToggleJoined, enabled = !fitnessClass.hasJoined
            ) {
                Text(
                    text = if (fitnessClass.hasJoined) "Joined" else "Join",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun ClassImage(imageUrl: String?) {
    if (imageUrl == null) {
        Icon(
            imageVector = Icons.Filled.SportsGymnastics,
            contentDescription = "icon pic",
            modifier = Modifier.size(48.dp)
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "image",
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ClassesFilterChips(selected: ClassCategory, onSelect: (ClassCategory) -> Unit) {
    val cats = ClassCategory.entries
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cats.size) { i ->
            val cat = cats[i]
            FilterChip(
                selected = selected == cat,
                onClick = { onSelect(cat) },
                label = { Text(cat.name) })
        }
    }
}