package com.cftc.PracticeApp.model

enum class DishCategory {Appetizers, Mains, Desserts, Drinks}

data class Dish(
    val id: String,
    val title: String,
    val description: String,
    val category: DishCategory,
    val price: Double,
    val imageUrl: String?,
)