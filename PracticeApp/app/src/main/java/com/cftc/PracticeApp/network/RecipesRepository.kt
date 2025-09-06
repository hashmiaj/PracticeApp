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
                "Creamy Garlic Parmesan Pasta",
                "Rich and creamy pasta with fresh garlic, parmesan cheese, and herbs.",
                "https://images.unsplash.com/photo-1711539137930-3fa2ae6cec60?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxkZWxpY2lvdXMlMjBwYXN0YSUyMGRpc2h8ZW58MXx8fHwxNzU3MTY2NzI3fDA&ixlib=rb-4.1.0&q=80&w=1080",
                RecipeCategory.QuickMeals,
                false
            )
        )
    }
}