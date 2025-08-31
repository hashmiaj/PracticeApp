package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.BookDetailsModel

data class BookDetailsUiState(
    val bookDetails: BookDetailsModel? = null,
    val isLoadingDetails: Boolean = false,
)