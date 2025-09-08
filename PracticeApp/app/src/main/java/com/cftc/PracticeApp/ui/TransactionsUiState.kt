package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.PaymentType
import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.model.TransactionType

data class TransactionsUiState(
    val list: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val selectedPaymentType: PaymentType = PaymentType.All,
    val selectedTransactionType: List<TransactionType> = emptyList(),
    val inputQuery: String = "",
    val appliedQuery: String = "",
) {
    private val filteredList: List<Transaction>
        get() = if (selectedPaymentType == PaymentType.All) list else list.filter { it.paymentType == selectedPaymentType }

    private val listByTransactionType: List<Transaction>
        get() = if (selectedTransactionType.isEmpty()) filteredList else filteredList.filter { it.transactionType in selectedTransactionType }

    val visibleList: List<Transaction>
        get() = if (appliedQuery.isBlank()) listByTransactionType else listByTransactionType.filter {
            it.matches(
                appliedQuery
            )
        }

    val showEmpty: Boolean
        get() = visibleList.isEmpty()
}

fun Transaction.matches(q: String): Boolean {
    return title.contains(q, ignoreCase = true)
}