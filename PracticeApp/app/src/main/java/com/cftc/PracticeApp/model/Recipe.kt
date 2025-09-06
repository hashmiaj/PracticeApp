package com.cftc.PracticeApp.model

enum class RecipeDifficulty { Easy, Medium, Hard }
enum class RecipeCategory { All, Breakfast, Lunch, Dinner, Dessert, Snacks, Drinks }

data class Recipe(
    val id: String,
    val title: String,
    val difficulty: RecipeDifficulty,
    val recipeImageUral: String?,
    val timeToCook: Int,
    val chefName: String,
    val chefProfilePicUrl: String?,
    val isFavorited: Boolean,
    val recipeCategory: RecipeCategory
)