package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.BookModel

data class MyLibraryUiState(
    val booksList: List<BookModel>? = null,
    val pageNumber: Int = 1,
    val isLoadingBooks: Boolean = false,
    val canLoadMore: Boolean = false,
)