package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory

data class RecipesUiState(
    val recipesList: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: RecipeCategory = RecipeCategory.All,
) {
    val visibleList: List<Recipe>
        get() = if (selectedCategory == RecipeCategory.All) recipesList else recipesList.filter { it.recipeCategory == selectedCategory }

    val showEmpty: Boolean
        get() = visibleList.isEmpty()
}