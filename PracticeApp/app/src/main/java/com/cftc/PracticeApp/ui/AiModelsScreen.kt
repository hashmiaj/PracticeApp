package com.cftc.PracticeApp.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.AiModel
import com.cftc.PracticeApp.model.ModelCategory
import com.cftc.PracticeApp.model.ModelStatus
import com.cftc.PracticeApp.viewmodel.AiModelsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiModelsScreen() {
    val viewModel: AiModelsViewModel = viewModel()
    val state: AiModelsUiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadAiModelsList() }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("AI Models") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            CategoryChips(selected = state.selected, onSelect = viewModel::selectCategory)

            HorizontalDivider(Modifier.padding(top = 4.dp))

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                state.error != null -> {}
                state.showEmpty -> EmptyListView()
                else -> AiModelsGrid(models = state.visible)
            }
        }
    }
}

@Composable
fun TopBar(title: String) {
    Text(text = title, style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(32.dp))
}


@Composable
private fun CategoryChips(
    selected: ModelCategory,
    onSelect: (ModelCategory) -> Unit
) {
    val cats =
        listOf(ModelCategory.All, ModelCategory.Vision, ModelCategory.Language, ModelCategory.Audio)
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cats.size) { i ->
            val cat = cats[i]
            FilterChip(
                selected = selected == cat,
                onClick = { onSelect(cat) },
                label = { Text(cat.name) }
            )
        }
    }
}

@Composable
fun EmptyListView() {
    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.ElectricalServices,
            contentDescription = "No Models available",
            modifier = Modifier
                .size(80.dp)
                .clip(RectangleShape),
        )
        Text(text = "No models available", style = MaterialTheme.typography.titleLarge)
        Text(
            text = "There are currently no AI Models in the catelog. Check back later for new additions.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun AiModelsGrid(models: List<AiModel>) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            count = models.size,
            key = { index -> models[index].id },
            contentType = { _ -> "models" }
        ) { index ->
            ModelCard(models[index])
        }
    }
}

@Composable
private fun ModelCard(model: AiModel) {
    OutlinedCard(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ModelIcon(url = model.imageUrl)

            Column(Modifier.weight(1f)) {
                Text(
                    text = model.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Spacer(Modifier.height(6.dp))
                StatusPill(model.status)
            }
        }
    }
}

@Composable
private fun ModelIcon(url: String?) {
    val shape: Shape = RoundedCornerShape(12.dp)
    if (url.isNullOrEmpty()) {
        // fallback box
        Box(
            Modifier
                .size(48.dp)
                .clip(shape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountTree, // any neutral icon
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(shape),
            contentScale = ContentScale.Crop
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
            text = if (status == ModelStatus.Online) "Online" else "Offline",
            color = content,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}