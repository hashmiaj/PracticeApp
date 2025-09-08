package com.cftc.PracticeApp.model

enum class BookCategory {All, Reading, Completed, Wishlist}

data class Book(
    val id: String,
    val title: String,
    val authorName: String,
    val category: BookCategory,
    val isFavorited: Boolean
)