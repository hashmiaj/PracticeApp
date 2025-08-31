package com.cftc.PracticeApp.model

enum class ModelCategory { All, Vision, Language, Audio, Empty }
enum class ModelStatus { Online, Offline }

data class AiModel(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val modelCategory: ModelCategory,
    val status: ModelStatus
)