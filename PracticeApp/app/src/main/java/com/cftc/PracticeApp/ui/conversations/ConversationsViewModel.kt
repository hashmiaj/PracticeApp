package com.cftc.PracticeApp.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.data.Conversation
import com.cftc.PracticeApp.data.FakeConversationsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConversationsViewModel(
    private val repo: FakeConversationsRepository = FakeConversationsRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(ConversationsUiState())
    val state = _state.asStateFlow()

    init {
        load()
        // Debounced search—standard interview pattern
        viewModelScope.launch {
            state
                .map { it.query }
                .debounce(250)
                .distinctUntilChanged()
                .collect { /* no-op; derived state uses query automatically */ }
        }
    }

    fun onQueryChange(newValue: String) {
        _state.update { it.copy(query = newValue) }
    }

    fun openSheet(id: String) {
        _state.update { it.copy(sheetForId = id) }
    }

    fun closeSheet() {
        _state.update { it.copy(sheetForId = null) }
    }

    fun markRead(id: String) = mutate(id) { it.copy(unreadCount = 0) }
    fun togglePin(id: String) = mutate(id) { it.copy(isPinned = !it.isPinned) }
    fun delete(id: String) {
        _state.update {
            it.copy(
                conversations = it.conversations.filterNot { c -> c.id == id },
                sheetForId = null
            )
        }
    }

    private fun mutate(id: String, block: (Conversation) -> Conversation) {
        _state.update {
            it.copy(
                conversations = it.conversations.map { c -> if (c.id == id) block(c) else c },
                sheetForId = null
            )
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val data = repo.loadConversations()
            _state.update { it.copy(isLoading = false, conversations = data) }
        }
    }
}
