package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Book
import com.cftc.PracticeApp.model.BookCategory

data class LibraryUiState(
    val list: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selected: BookCategory = BookCategory.All,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    private val filteredList: List<Book>
        get() = if (selected == BookCategory.All) list else list.filter { it.category == selected }

    val visibleList: List<Book>
        get() = if (appliedQuery.isBlank()) filteredList else filteredList.filter {
            it.matches(
                appliedQuery
            )
        }

    val showEmpty: Boolean
        get() = !isLoading && visibleList.isEmpty()

}

fun Book.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true) || authorName.contains(q, ignoreCase = true)
}