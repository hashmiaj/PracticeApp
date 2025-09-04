package com.cftc.PracticeApp.ui

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.viewmodel.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen() {
    val vm: RecipesViewModel = viewModel()
    val state: RecipesUiState by vm.state.collectAsState()

    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            vm.getAllRecipes()
            pullToRefreshState.endRefresh()
        }
    }

    Scaffold { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.inputQuery,
                onValueChange = vm::onQueryChanged,
                placeholder = { Text("Search recipes...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search icon"
                    )
                }
            )

            HorizontalDivider()

            FilterChips(state.selectedCategory, vm::selectCategory)

            HorizontalDivider()

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    ShowEmpty()
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .nestedScroll(pullToRefreshState.nestedScrollConnection)
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                count = state.visibleList.size,
                                key = { index -> state.visibleList[index].id },
                                contentType = { _ -> "Recipe" }
                            ) { index ->
                                RecipeCard(
                                    recipe = state.visibleList[index],
                                    onFavoriteClicked = vm::toggleFavorite
                                )
                            }
                        }
                        val showPullToRefresh =
                            pullToRefreshState.isRefreshing || pullToRefreshState.progress > 0f
                        if (showPullToRefresh) {
                            PullToRefreshContainer(
                                state = pullToRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onFavoriteClicked: (String) -> Unit) {
    OutlinedCard(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
        RecipeImage(recipe.imageUrl, { onFavoriteClicked(recipe.id) }, recipe.isFavorited)
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Timer, contentDescription = "Recipe time icon")
                Text(
                    text = "${recipe.timeToCook} minutes",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun RecipeImage(imageUrl: String?, onFavoriteClicked: () -> Unit, isFavorited: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        if (imageUrl == null) {
            Icon(
                imageVector = Icons.Filled.Fastfood,
                contentDescription = "Placeholder image for Recipe",
                modifier = Modifier
                    .size(48.dp)
                    .fillMaxWidth()
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Image for recipe",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillWidth
            )
        }

        IconButton(
            onClick = onFavoriteClicked,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = if (isFavorited) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = "Favorite star",
                tint = if (isFavorited) Color(0xFFFFEE00) else Color(0xFF000000)
            )
        }
    }
}

@Composable
fun ShowEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.Search,
            contentDescription = "Empty Search Icon",
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "No recipes found",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Try adjusting your search or filter criteria to find more recipes.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FilterChips(selected: RecipeCategory, onCategorySelected: (RecipeCategory) -> Unit) {
    val cats = RecipeCategory.entries
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cats.size) { i ->
            val cat = cats[i]
            FilterChip(
                selected = selected == cat,
                onClick = { onCategorySelected(cat) },
                label = { Text(cat.name) }
            )
        }
    }

}