package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory

data class RecipesUiState(
    val recipesList: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: RecipeCategory = RecipeCategory.All,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    private val filteredList: List<Recipe>
        get() = if (selectedCategory == RecipeCategory.All) recipesList else recipesList.filter { it.category == selectedCategory }

    val visibleList: List<Recipe>
        get() = if (appliedQuery.isBlank()) filteredList else filteredList.filter { it.matches(appliedQuery) }

    val showEmpty : Boolean
        get() = visibleList.isEmpty() && !isLoading

}

fun Recipe.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true)
}