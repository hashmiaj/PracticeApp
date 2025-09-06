package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Recipe
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.model.RecipeDifficulty
import kotlinx.coroutines.delay

class RecipesRepository() {
    suspend fun getAllRecipes(): List<Recipe> {
        delay(2000)
        return listOf(
            Recipe(
                "1",
                "Fluffy Pancakes",
                RecipeDifficulty.Easy,
                "https://images.unsplash.com/photo-1658429357971-e8a3aa8c08c5?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxicmVha2Zhc3QlMjBwYW5jYWtlcyUyMGZvb2R8ZW58MXx8fHwxNzU3MDk4NTY4fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                20,
                "Sarah Wilson",
                "https://images.unsplash.com/photo-1514517417892-339518578cdb?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxmb29kJTIwYmxvZ2dlciUyMHdvbWFufGVufDF8fHx8MTc1Njk5MDczOXww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                false,
                RecipeCategory.Breakfast
            )
        )
    }
}