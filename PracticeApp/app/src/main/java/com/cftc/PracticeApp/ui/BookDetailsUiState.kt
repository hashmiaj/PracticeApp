package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Book

data class BookDetailsUiState(
    val book: Book? = null,
    val isLoading: Boolean = false,
)