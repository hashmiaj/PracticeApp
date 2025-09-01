package com.cftc.PracticeApp.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String?
)