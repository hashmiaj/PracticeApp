package com.cftc.PracticeApp.model

data class ResearchPaper(
    val id : String,
    val title : String,
    val author : String,
    val year : String,
    val isBookmarked : Boolean,
)