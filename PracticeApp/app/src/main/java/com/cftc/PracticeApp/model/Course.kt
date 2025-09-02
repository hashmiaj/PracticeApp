package com.cftc.PracticeApp.model

enum class CourseCategory {All, Design, AIML, Mobile, Web}

data class Course(
    val id: String,
    val title: String,
    val prof: String,
    val category : CourseCategory,
    val rating : Double,
    val imageUrl: String?,
    val isFavorite: Boolean,
)