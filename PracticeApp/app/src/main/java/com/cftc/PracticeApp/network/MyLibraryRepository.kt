package com.cftc.PracticeApp.network

import androidx.compose.runtime.rememberUpdatedState
import com.cftc.PracticeApp.model.BookModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyLibraryRepository() {
    private val _booksCache = MutableStateFlow<List<BookModel>>(emptyList())

    suspend fun getMyLibraryBooks(page: Int, pageSize: Int): List<BookModel> {
        require(page >= 1 && pageSize > 0) { "Invalid paging params" }

        val startIndex = (page - 1) * pageSize
        val endIndex = startIndex + pageSize

        val cached = _booksCache.value
        if (cached.size >= endIndex) {
            return cached.subList(startIndex, endIndex)
        }

        delay(3000)

        val start = cached.size + 1
        val end = endIndex

        val newBooks = (start..end).map { i ->
            BookModel(
                i.toString(),
                "Book number $i",
                "Author of $i",
                "Description of $i",
                "https://plus.unsplash.com/premium_photo-1669652639337-c513cc42ead6?q=80&w=774&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                false,
            )
        }
        _booksCache.update { it + newBooks }

        val updated = _booksCache.value
        return updated.subList(startIndex, endIndex)
    }
}