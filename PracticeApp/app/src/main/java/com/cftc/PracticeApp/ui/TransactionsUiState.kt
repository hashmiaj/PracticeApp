package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.model.TransactionType

data class TransactionsUiState(
    val transactionsList: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val selectedType: TransactionType = TransactionType.All,
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    private val filteredList: List<Transaction>
        get() = if (selectedType == TransactionType.All) transactionsList else transactionsList.filter { it.type == selectedType }

    val visibleList: List<Transaction>
        get() = if (appliedQuery.isBlank()) filteredList else filteredList.filter {
            it.matches(
                appliedQuery
            )
        }

    val showEmpty: Boolean
        get() = !isLoading && visibleList.isEmpty()

}

fun Transaction.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true) || type.name.contains(q, ignoreCase = true)
}