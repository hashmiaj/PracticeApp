package com.cftc.PracticeApp.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.model.RecipeDifficulty
import com.cftc.PracticeApp.viewmodel.RecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen() {
    val vm: RecipesViewModel = viewModel()
    val state: RecipesUiState by vm.state.collectAsState()

    val snackbar = remember { SnackbarHostState() }

    LaunchedEffect(Unit) { vm.getAllRecipes() }

    Scaffold(
        topBar = {
            TopAppBar({
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Recipe Browser", style = MaterialTheme.typography.titleLarge)
                    Text("Discover delicious recipes", style = MaterialTheme.typography.bodyMedium)
                }
            })
        },
        snackbarHost = { SnackbarHost(snackbar) },
        floatingActionButton = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = "Add recipe icon",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalDivider()

            FilterChips(state.selectedCategory, vm::onSelectCategory)

            when {
                state.isLoading -> {
                    Box(
                        Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.showEmpty -> {
                    EmptyScreen()
                }

                else -> {
                    val sectionOrder = listOf(
                        RecipeCategory.Breakfast,
                        RecipeCategory.Lunch,
                        RecipeCategory.Dinner,
                        RecipeCategory.Dessert,
                        RecipeCategory.Snacks,
                        RecipeCategory.Drinks
                    )
                    val sections = remember(state.visibleList) {
                        sectionOrder.mapNotNull { category ->
                            val items = state.visibleList.filter { it.recipeCategory == category }
                            if (items.isNotEmpty()) category to items else null
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        sections.forEach { (category, recipes) ->
                            stickyHeader(key = category.name) {
                                SectionHeader(category, recipes.size)
                            }
                            items(recipes.size) { i ->
                                RecipeCard(recipes[i])
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    OutlinedCard(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RecipeImage(recipe.recipeImageUral)
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
                    DifficultyTimeRow(recipe.difficulty, recipe.timeToCook)
                }
                Icon(
                    imageVector = Icons.Outlined.StarBorder,
                    contentDescription = "star"
                )
            }
            RecipeChefRow(recipe.chefName, recipe.chefProfilePicUrl)
        }
    }
}

@Composable
fun RecipeChefRow(chefName: String, chefProfilePicUrl: String?) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(20.dp)) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AsyncImage(
                model = chefProfilePicUrl,
                contentDescription = "Chef Profile pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Text(
                text = chefName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun DifficultyTimeRow(difficulty: RecipeDifficulty, timeToCook: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = difficulty.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(8.dp)
            )
        }
        Icon(
            imageVector = Icons.Outlined.Timer,
            contentDescription = ""
        )
        Text(
            text = "$timeToCook min",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun RecipeImage(imageURL: String?) {
    if (imageURL == null) {
        Icon(
            imageVector = Icons.Filled.Fastfood,
            contentDescription = "Placeholder icon",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(20.dp))
        )
    } else {
        AsyncImage(
            model = imageURL,
            contentDescription = "Image for recipe",
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SectionHeader(category: RecipeCategory, numOfCategories: Int) {
    val recipeStr = if (numOfCategories > 1) "recipes" else "recipe"
    Column {
        Text(text = category.name, style = MaterialTheme.typography.titleLarge)
        Text(text = "$numOfCategories $recipeStr", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun EmptyScreen() {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Fastfood,
            contentDescription = "Empty icon",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Text(
            text = "No recipes in this category! Please select another category or add a recipe!",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun FilterChips(selected: RecipeCategory, onCategorySelected: (RecipeCategory) -> Unit) {
    val cats = RecipeCategory.entries

    LazyRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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