package com.cftc.PracticeApp.model

import androidx.compose.runtime.Immutable

@Immutable
data class BlogPost(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val description: String
)
