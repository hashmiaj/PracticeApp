package com.cftc.PracticeApp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.Author
import com.cftc.PracticeApp.model.ChatMessage
import com.cftc.PracticeApp.ui.ChatUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    private var lastId = 0

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(2000)
            var list: MutableList<ChatMessage> =
                emptyList<ChatMessage>() as MutableList<ChatMessage>
            list.addAll(_state.value.listOfMessages)
            val userMessageId = lastId++
            val userChatMessage = ChatMessage(userMessageId.toString(), message, Author.User)

            val aiMessageId = lastId++
            val aiChatMessage = ChatMessage(aiMessageId.toString(), message, Author.AI)

            list.add(userChatMessage)
            list.add(aiChatMessage)

            _state.update { it.copy(isLoading = false, listOfMessages = list) }
        }
    }
}