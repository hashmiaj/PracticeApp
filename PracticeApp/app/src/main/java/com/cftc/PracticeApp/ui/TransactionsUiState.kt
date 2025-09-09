package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Transaction

data class TransactionsUiState(
    val list: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    val visibleList: List<Transaction>
        get() = if (appliedQuery.isBlank()) list else list.filter { it.matches(appliedQuery) }

    val showEmpty: Boolean
        get() = !isLoading && visibleList.isEmpty()
}

fun Transaction.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true)
}