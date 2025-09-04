package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory
import kotlinx.coroutines.delay

class RecipesRepository() {
    suspend fun getAllRecipes(): List<Recipe> {
        delay(2000)
        return listOf(
            Recipe(
                "1",
                "Avocado Toast Supreme",
                "Creamy avocado on sourdough with cherry tomatoes and feta cheese",
                "https://images.unsplash.com/photo-1687276287139-88f7333c8ca4?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhdm9jYWRvJTIwdG9hc3R8ZW58MXx8fHwxNzU2OTIwMzAzfDA&ixlib=rb-4.1.0&q=80&w=1080",
                RecipeCategory.Breakfast,
                10,
                false
            )
        )
    }
}