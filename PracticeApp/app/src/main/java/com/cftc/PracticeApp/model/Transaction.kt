package com.cftc.PracticeApp.model

enum class TransactionType { All, Food, Travel, Shopping, Bills }

data class Transaction(
    val id: String,
    val title: String,
    val price: Double,
    val type: TransactionType,
    val date: String,
    val isFavorited: Boolean
)
