package com.cftc.PracticeApp.ui

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Outlet
import androidx.compose.material.icons.filled.SettingsBackupRestore
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.AiModel
import com.cftc.PracticeApp.model.ModelCategory
import com.cftc.PracticeApp.model.ModelStatus
import com.cftc.PracticeApp.viewmodel.AiModelsViewModel
import com.google.android.material.color.MaterialColors
import org.jetbrains.annotations.Async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiModelsScreen() {
    val viewModel: AiModelsViewModel = viewModel()
    val state: AiModelsUiState by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            LargeTopAppBar(
                title = { Text("AI Models") }, scrollBehavior = scrollBehavior
            )
        }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            // SEARCH
            SearchField(
                value = state.inputQuery,
                onValueChange = viewModel::onQueryChange,
                placeholder = "Search models…"
            )


            FilterChips(selected = state.selected, onSelect = viewModel::selectCategory)

            HorizontalDivider()

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {}
                state.showEmpty -> {
                    EmptyScreen(
                        query = state.appliedQuery,
                        onReset = viewModel::resetFilters
                    )
                }

                else -> {
                    AiModelsGrid(state.visible)
                }
            }
        }
    }
}


@Composable
fun FilterChips(selected: ModelCategory, onSelect: (category: ModelCategory) -> Unit) {
    val cats = listOf(
        ModelCategory.All,
        ModelCategory.Vision,
        ModelCategory.Language,
        ModelCategory.Audio,
        ModelCategory.Empty
    )
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
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


@Composable
fun EmptyScreen(query: String, onReset: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.ElectricalServices,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = if (query.isBlank()) "No models available"
            else "No models found", style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = if (query.isBlank()) "There are currently no AI Models in the catelog."
            else "No models found for \"$query\".",
            maxLines = 3,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = if (query.isBlank()) "Check back later for new additions."
            else "Try a different search term or reset your filters",
            maxLines = 3,
            style = MaterialTheme.typography.bodyMedium
        )

        if (query.isNotBlank()) {
            Spacer(Modifier.size(16.dp))
            OutlinedButton(onClick = onReset) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(
                        imageVector = Icons.Filled.SettingsBackupRestore,
                        contentDescription = null
                    )
                    Text("Reset filters")
                }

            }
        }
    }
}

@Composable
fun AiModelsGrid(models: List<AiModel>) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = models.size,
            key = { index -> models[index].id },
            contentType = { _ -> "models" }) { index ->
            ModelCard(models[index])
        }
    }
}

@Composable
fun ModelCard(model: AiModel) {
    OutlinedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ModelImage(model.imageUrl)
            Text(text = model.name, style = MaterialTheme.typography.titleMedium)
            StatusPill(model.status)
        }
    }
}


@Composable
fun ModelImage(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image for AI model",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(32.dp)
        )
    } else {
        Icon(
            imageVector = Icons.Filled.Outlet,
            contentDescription = "Image for AI model",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun StatusPill(status: ModelStatus) {
    val (container, content) = when (status) {
        ModelStatus.Online -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        ModelStatus.Offline -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
    }

    Surface(shape = RoundedCornerShape(999.dp), color = container) {
        Text(
            text = if (status == ModelStatus.Online) ModelStatus.Online.name else ModelStatus.Offline.name,
            color = content,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Outlined.Close, contentDescription = "Clear")
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}