package com.cftc.PracticeApp.model

enum class ClassCategory {All, Yoga, HIIT, Dance, Pilates}

data class FitnessClass(
    val id: String,
    val title: String,
    val category: ClassCategory,
    val date: String, // ISO 2025-09-08
    val startTime: String,
    val endTime: String,
    val coach: String,
    val imageUrl: String?,
    val hasJoined: Boolean,
)