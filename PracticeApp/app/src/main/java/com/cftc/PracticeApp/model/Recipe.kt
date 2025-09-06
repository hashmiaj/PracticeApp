package com.cftc.PracticeApp.model

enum class RecipeCategory {Vegan, QuickMeals, Desserts, HighProtein}

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val category : RecipeCategory,
    val isFavorited : Boolean,
)