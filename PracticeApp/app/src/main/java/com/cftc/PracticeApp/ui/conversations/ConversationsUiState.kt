package com.cftc.PracticeApp.ui.conversations

import com.cftc.PracticeApp.data.Conversation

data class ConversationsUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val conversations: List<Conversation> = emptyList(),
    val sheetForId: String? = null // when non-null, show bottom sheet for that conversation
) {
    // Derived state (simple and interview-friendly)
    val filtered: List<Conversation> = conversations
        .filter { it.name.contains(query, true) || it.lastMessage.contains(query, true) }
        .sortedWith(
            compareByDescending<Conversation> { it.isPinned }
                .thenByDescending { it.timestampEpochMillis }
        )
    val showEmpty = !isLoading && filtered.isEmpty()
}
