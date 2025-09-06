package com.cftc.PracticeApp.ui

import android.R
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.viewmodel.RecipesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipesScreen() {
    val vm: RecipesViewModel = viewModel()
    val state: RecipesUiState by vm.state.collectAsState()

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search input
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::updateSearchQuery,
                placeholder = { Text("Search recipes...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search trailing icon"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // filter chips
            FilterChips(state.selectedList, vm::selectFilterCategory)

            HorizontalDivider()

            // list
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    ShowEmptyScreen(vm::onClearFilters)
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            count = state.visibleList.size,
                            key = { index -> state.visibleList[index].id },
                            contentType = { _ -> "Recipe" }
                        ) { index ->
                            // Card
                            RecipeCard(
                                state.visibleList[index],
                                vm::toggleFavorite,
                                snackbar,
                                scope
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    onFavoriteToggle: (String) -> Unit,
    snackbar: SnackbarHostState,
    scope: CoroutineScope
) {
    Box(modifier = Modifier.padding(16.dp)) {
        OutlinedCard {
            RecipeImage(recipe.imageUrl)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        IconButton(
            onClick = {
                onFavoriteToggle(recipe.id)
                scope.launch {
                    snackbar.showSnackbar(message = "Toggled favorite icon in the recipe ${recipe.title}")
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (recipe.isFavorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite recipe icon",
                tint = if (recipe.isFavorited) Color(0xFFFF0000) else Color(0xFF000000)
            )
        }
    }
}

@Composable
fun RecipeImage(imageUrl: String?) {
    if (imageUrl == null) {
        Icon(
            imageVector = Icons.Filled.Restaurant,
            contentDescription = "Recipe image icon placeholder",
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .padding(bottom = 16.dp)
        )
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Recipe image",
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ShowEmptyScreen(onClearFilters: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Restaurant,
            contentDescription = "Empty screen icon",
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "No recipes match your filters",
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Try adjusting your search or clearing some filters to see more recipes",
            style = MaterialTheme.typography.titleLarge,
        )

        OutlinedButton(onClick = { onClearFilters() }) {
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Clear filters icon")
                Text("Clear Filters", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Composable
fun FilterChips(selectedList: List<RecipeCategory>, onSelectCategory: (RecipeCategory) -> Unit) {
    val cats = RecipeCategory.entries
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(cats.size) { i ->
            val cat = cats[i]
            val isSelected = selectedList.contains(cat)
            FilterChip(
                selected = isSelected,
                onClick = { onSelectCategory(cat) },
                label = { Text(cat.name) }
            )
        }
    }
}