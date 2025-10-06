package com.cftc.PracticeApp.model

enum class Author { User, AI }

data class ChatMessage(
    val id: String,
    val messageContent: String,
    val author: Author
)