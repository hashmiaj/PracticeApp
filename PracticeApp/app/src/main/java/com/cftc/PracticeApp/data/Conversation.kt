package com.cftc.PracticeApp.data

data class Conversation(
    val id: String,
    val name: String,
    val lastMessage: String,
    val timestampEpochMillis: Long,
    val unreadCount: Int,           // 0 == none
    val avatarInitials: String,     // e.g. "AH"
    val isPinned: Boolean = false
)
