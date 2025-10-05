package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.ChatThread
import kotlinx.coroutines.flow.update

enum class ChatsCategory { All, Unread, Favorites, Groups }

data class ChatsUiState(
    val list: List<ChatThread> = emptyList(),
    val isLoading: Boolean = false,
    val selectedCategory: ChatsCategory = ChatsCategory.All,
    val inputQuery: String = "",
    val appliedQuery: String = ""
) {

    val visibleList: List<ChatThread>
        get() {
            val byCategory = when (selectedCategory) {
                ChatsCategory.All -> list
                ChatsCategory.Unread -> list.filter { it.numOfUnreadMessages > 0 }
                ChatsCategory.Favorites -> list.filter { it.isFavorite }
                ChatsCategory.Groups -> list.filter { it.isGroup }
            }
            return if (appliedQuery.isBlank()) byCategory.sortedByDescending { it.isPinned }
            else byCategory.filter { it.matches(appliedQuery) }.sortedByDescending { it.isPinned }
        }

    val showEmptyList: Boolean
        get() = !isLoading && visibleList.isEmpty()
}

fun ChatThread.matches(query: String): Boolean {
    return authorName.contains(query, ignoreCase = true) || previewMessage.contains(
        query,
        ignoreCase = true
    )
}