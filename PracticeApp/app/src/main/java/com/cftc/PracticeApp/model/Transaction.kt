package com.cftc.PracticeApp.model

enum class PaymentType {All,  Income, Expense }
enum class TransactionType { Food, Transport, Shopping, Bills, Health, Other }

data class Transaction(
    val id: String,
    val title: String,
    val price: Double,
    val date: String,
    val cardDetails: String,
    val isFavorited: Boolean,
    val paymentType: PaymentType,
    val transactionType: TransactionType
)