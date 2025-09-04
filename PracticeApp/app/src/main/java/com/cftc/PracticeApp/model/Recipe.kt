package com.cftc.PracticeApp.model

enum class RecipeCategory { All, Breakfast, Lunch, Dinner, Dessert, Vegan }

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val category: RecipeCategory,
    val timeToCook: Int,
    val isFavorited: Boolean,
)