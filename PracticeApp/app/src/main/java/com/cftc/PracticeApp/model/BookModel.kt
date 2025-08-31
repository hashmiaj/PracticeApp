package com.cftc.PracticeApp.model

data class BookModel(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false,
)