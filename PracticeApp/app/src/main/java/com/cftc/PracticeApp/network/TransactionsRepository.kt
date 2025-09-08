package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Transaction
import com.cftc.PracticeApp.model.TransactionType
import kotlinx.coroutines.delay

class TransactionsRepository() {
    suspend fun getAllTransactions(): List<Transaction> {
        delay(2000)
        return listOf(
            Transaction(
                id = "1",
                title = "Starbucks",
                price = -5.25,
                type = TransactionType.Food,
                date = "2025-01-08",
                isFavorited = false
            ),
            Transaction(
                id = "2",
                title = "Uber",
                price = -12.50,
                type = TransactionType.Travel,
                date = "2025-01-08",
                isFavorited = true
            ),
            Transaction(
                id = "3",
                title = "Amazon",
                price = -89.99,
                type = TransactionType.Shopping,
                date = "2025-01-07",
                isFavorited = false
            ),
            Transaction(
                id = "4",
                title = "Electric Company",
                price = -156.33,
                type = TransactionType.Bills,
                date = "2025-01-06",
                isFavorited = false
            ),
            Transaction(
                id = "5",
                title = "McDonald's",
                price = -8.75,
                type = TransactionType.Food,
                date = "2025-01-06",
                isFavorited = true
            ),
            Transaction(
                id = "6",
                title = "Delta Airlines",
                price = -425.00,
                type = TransactionType.Travel,
                date = "2025-01-05",
                isFavorited = false
            ),
            Transaction(
                id = "7",
                title = "Target",
                price = -67.45,
                type = TransactionType.Shopping,
                date = "2025-01-05",
                isFavorited = false
            ),
            Transaction(
                id = "8",
                title = "Internet Provider",
                price = -79.99,
                type = TransactionType.Bills,
                date = "2025-01-04",
                isFavorited = false
            ),
            Transaction(
                id = "9",
                title = "Chipotle",
                price = -11.25,
                type = TransactionType.Food,
                date = "2025-01-04",
                isFavorited = false
            ),
            Transaction(
                id = "10",
                title = "Gas Station",
                price = -45.00,
                type = TransactionType.Travel,
                date = "2025-01-03",
                isFavorited = true
            ),
            Transaction(
                id = "11",
                title = "Best Buy",
                price = -299.99,
                type = TransactionType.Shopping,
                date = "2025-01-02",
                isFavorited = false
            ),
            Transaction(
                id = "12",
                title = "Coffee Bean",
                price = -4.50,
                type = TransactionType.Food,
                date = "2025-01-02",
                isFavorited = false
            )
        )
    }
}