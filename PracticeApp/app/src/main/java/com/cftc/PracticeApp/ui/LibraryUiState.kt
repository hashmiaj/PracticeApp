package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Book

data class LibraryUiState(
    val list: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    val visibleList: List<Book>
        get() = if (appliedQuery.isBlank()) list else list.filter { it.matches(appliedQuery) }

    val showEmpty: Boolean
        get() = visibleList.isEmpty() && !isLoading
}

fun Book.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true)
}