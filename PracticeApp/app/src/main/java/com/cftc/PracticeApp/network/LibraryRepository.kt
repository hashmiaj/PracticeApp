package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.Book
import com.cftc.PracticeApp.model.BookCategory
import kotlinx.coroutines.delay

class LibraryRepository() {
    suspend fun getAllBooks(): List<Book> {
        delay(2000)
        return listOf(
            Book(
                "1",
                "Atomic Habits",
                "James Clear",
                BookCategory.Reading,
                false
            ),
            Book(
                "2",
                "Atomic Habits",
                "James Clear",
                BookCategory.Wishlist,
                true
            ),
            Book(
                "3",
                "Atomic Habits",
                "James Clear",
                BookCategory.Completed,
                false
            ),
            Book(
                "4",
                "Atomic Habits",
                "James Clear",
                BookCategory.Reading,
                false
            ),
            Book(
                "5",
                "Atomic Habits",
                "James Clear",
                BookCategory.Completed,
                false
            ),
            Book(
                "6",
                "Atomic Habits",
                "James Clear",
                BookCategory.Reading,
                true
            ),
            Book(
                "7",
                "Atomic Habits",
                "James Clear",
                BookCategory.Reading,
                false
            ),
            Book(
                "8",
                "Atomic Habits",
                "James Clear",
                BookCategory.Completed,
                false
            ),
        )
    }
}