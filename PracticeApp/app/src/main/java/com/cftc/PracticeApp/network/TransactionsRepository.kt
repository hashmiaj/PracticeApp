package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Transaction
import kotlinx.coroutines.delay

class TransactionsRepository() {
    suspend fun getAllTransactions(): List<Transaction> {
        delay(2000)
        return listOf(
            Transaction(
                "1",
                "Tax Refund",
                "IRS",
                -174.00,
                "2025-09-06",
                false
            ),
            Transaction(
                "2",
                "Income",
                "Employer",
                1065.00,
                "2025-04-06",
                false
            ),
        )

    }
}