package com.cftc.PracticeApp.model

data class Transaction(
    val id: String,
    val title: String,
    val merchant: String,
    val cost : Double,
    val date : String, // ISO 2025-09-02
    val isFavorited: Boolean,
)