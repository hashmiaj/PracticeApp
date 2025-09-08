package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.PaymentType
import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.model.TransactionType
import kotlinx.coroutines.delay

class TransactionRepository() {
    suspend fun getAllTransactions(): List<Transaction> {
        delay(2000)
        return listOf(
            Transaction(
                "1",
                "Grocery Shopping",
                127.50,
                "2025-01-06",
                "Visa ****1234",
                false,
                PaymentType.Expense,
                TransactionType.Food
            )
        )
    }
}