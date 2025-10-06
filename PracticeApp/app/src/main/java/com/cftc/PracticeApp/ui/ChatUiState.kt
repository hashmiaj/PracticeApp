package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.ChatMessage

data class ChatUiState(
    val listOfMessages: List<ChatMessage> = emptyList(),
    val isIncognito: Boolean = false,
    val isLoading: Boolean = false,
) {
    val showEmpty: Boolean
        get() = !isLoading && listOfMessages.isEmpty()
}