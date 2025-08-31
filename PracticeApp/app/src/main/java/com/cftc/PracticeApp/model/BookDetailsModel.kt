package com.cftc.PracticeApp.model

data class BookDetailsModel(
    val id: String,
    val imageUrl: String? = null,
    val title: String,
    val author: String,
    val rating: String,
    val reviewsCount: Int,
    val genresList: List<String>,
    val description: String,
    val isInList: Boolean = false,
)