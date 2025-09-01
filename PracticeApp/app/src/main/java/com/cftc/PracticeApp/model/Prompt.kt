package com.cftc.PracticeApp.model

enum class PromptCategory { All, Writing, Code, Analysis, Fun}

data class Prompt(
    val id: String,
    val title: String,
    val snippet: String,
    val tag: String,
    val category: PromptCategory,
    val isFavorited: Boolean,
)

