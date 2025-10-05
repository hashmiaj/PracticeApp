package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.ChatThread
import kotlinx.coroutines.delay

class ChatsRepository() {
    suspend fun getAllChats(): List<ChatThread> {
        delay(2000)
        return listOf(
            ChatThread(
                id = "1",
                authorName = "Maya Ali",
                previewMessage = "Dinner tonight?",
                previewMessageTime = "2025-10-04 16:15:00",
                numOfUnreadMessages = 0,
                isPinned = true,
                isFavorite = true,
                isGroup = false,
                profilePhoto = "https://randomuser.me/api/portraits/women/1.jpg"
            ),
            ChatThread(
                id = "2",
                authorName = "Ali Group Chat",
                previewMessage = "Abdullah: Let’s meet at 7",
                previewMessageTime = "2025-10-04 15:22:00",
                numOfUnreadMessages = 5,
                isPinned = false,
                isFavorite = false,
                isGroup = true,
                profilePhoto = "https://randomuser.me/api/portraits/men/11.jpg"
            ),
            ChatThread(
                id = "3",
                authorName = "Work Chat",
                previewMessage = "Deadline moved to Monday",
                previewMessageTime = "2025-10-03 10:45:00",
                numOfUnreadMessages = 2,
                isPinned = false,
                isFavorite = false,
                isGroup = true,
                profilePhoto = "https://randomuser.me/api/portraits/men/12.jpg"
            ),
            ChatThread(
                id = "4",
                authorName = "Sara Khan",
                previewMessage = "Loved that new Threads feature!",
                previewMessageTime = "2025-10-02 22:30:00",
                numOfUnreadMessages = 0,
                isPinned = false,
                isFavorite = true,
                isGroup = false,
                profilePhoto = "https://randomuser.me/api/portraits/women/2.jpg"
            ),
            ChatThread(
                id = "5",
                authorName = "Mom ❤️",
                previewMessage = "Beta, call me when you’re free.",
                previewMessageTime = "2025-10-04 09:00:00",
                numOfUnreadMessages = 1,
                isPinned = true,
                isFavorite = true,
                isGroup = false,
                profilePhoto = "https://randomuser.me/api/portraits/women/3.jpg"
            ),
            ChatThread(
                id = "6",
                authorName = "Gym Group",
                previewMessage = "Tomorrow 7am workout!",
                previewMessageTime = "2025-10-04 07:45:00",
                numOfUnreadMessages = 3,
                isPinned = false,
                isFavorite = false,
                isGroup = true,
                profilePhoto = "https://randomuser.me/api/portraits/men/14.jpg"
            )
        )
    }
}