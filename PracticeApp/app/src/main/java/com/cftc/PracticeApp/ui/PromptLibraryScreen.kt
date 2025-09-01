package com.cftc.PracticeApp.ui

import android.R
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.Prompt
import com.cftc.PracticeApp.model.PromptCategory
import com.cftc.PracticeApp.viewmodel.PromptLibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromptLibraryScreen() {
    val viewModel: PromptLibraryViewModel = viewModel()
    val state: PromptsUiState by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            LargeTopAppBar(
                title = { Text("Prompt Library") }, scrollBehavior = scrollBehavior
            )
        }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            // Search Bar
            SearchBar(
                value = state.inputQuery,
                onValueChange = viewModel::onQueryChange,
                placeholder = "Search templates..."
            )

            // Filter Chips List
            FilterChipsList(state.selected, viewModel::selectCategory)

            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    EmptyScreen(state.inputQuery, viewModel::resetFilters)
                }

                else -> {
                    ListOfPrompts(state.visibleList, listState)
                }
            }

        }
    }
}


@Composable
fun SearchBar(
    value: String, onValueChange: (String) -> Unit, placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(Icons.Outlined.Close, contentDescription = "Clear")
                }
            }
        },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
fun FilterChipsList(selected: PromptCategory, onSelect: (category: PromptCategory) -> Unit) {
    val cats = listOf(
        PromptCategory.All,
        PromptCategory.Writing,
        PromptCategory.Code,
        PromptCategory.Analysis,
        PromptCategory.Fun,
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
fun ListOfPrompts(list: List<Prompt>, listState: LazyListState) {
    val sectionOrder = listOf(
        PromptCategory.Writing,
        PromptCategory.Code,
        PromptCategory.Analysis,
        PromptCategory.Fun
    )

    val sections = remember(list) {
        sectionOrder.mapNotNull { category ->
            val items = list.filter { it.category == category }
            if (items.isNotEmpty()) category to items else null
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        sections.forEach { (category, itemsInCategory)  ->
            stickyHeader(key = "hdr-${category.name}") {
                SectionHeader(category)
            }
            items(
                count = itemsInCategory.size,
                key = {index -> itemsInCategory[index].id },
                contentType = {_ -> "prompts"}
            ) { index ->
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = list[index].title, style = MaterialTheme.typography.titleMedium)
                    Text(text = list[index].snippet, style = MaterialTheme.typography.bodyMedium)
                    TagPill(list[index].tag)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(category: PromptCategory) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Text(
            text = category.name.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun TagPill(tag: String) {
    val (container, content) = MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
    Surface(shape = RoundedCornerShape(999.dp), color = container) {
        Text(
            text = tag,
            color = content,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun EmptyScreen(inputQuery: String, onReset: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "No templates match",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "No templates found for $inputQuery",
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = "Try a different search term or reset your filters",
            style = MaterialTheme.typography.bodyMedium,
        )
        OutlinedButton(onClick = { onReset() }) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.LockReset,
                    contentDescription = null
                )
                Text(
                    text = "Reset filters",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}