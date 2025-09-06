package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory

data class RecipesUiState(
    val recipesList: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val selectedList: List<RecipeCategory> = emptyList(),
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    private val filteredList: List<Recipe>
        get() = if (selectedList.isEmpty()) {
            recipesList
        } else {
            recipesList.filter { it.category in selectedList }
        }

    val visibleList: List<Recipe>
        get() = if (appliedQuery.isBlank()) filteredList else filteredList.filter {
            it.matches(
                appliedQuery
            )
        }

    val showEmpty: Boolean
        get() = visibleList.isEmpty() && !isLoading
}

fun Recipe.matches(query: String): Boolean =
    title.contains(query, ignoreCase = true)
