package com.cftc.PracticeApp.model

data class ChatThread(
    val id: String,
    val authorName: String,
    val previewMessage: String,
    val previewMessageTime: String, // "2025-10-04 15:23:00"
    val numOfUnreadMessages: Int,
    val profilePhoto : String?,
    val isPinned: Boolean,
    val isFavorite : Boolean,
    val isGroup: Boolean,
)