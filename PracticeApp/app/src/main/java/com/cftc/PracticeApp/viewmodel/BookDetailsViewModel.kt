package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.LibraryRepository
import com.cftc.PracticeApp.ui.BookDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(): ViewModel() {

    private val _state = MutableStateFlow(BookDetailsUiState())
    val state : StateFlow<BookDetailsUiState> = _state.asStateFlow()

    fun getBookById(bookId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val book = LibraryRepository.getBookById(bookId)
            _state.update { it.copy(book = book, isLoading = false) }
        }
    }
}