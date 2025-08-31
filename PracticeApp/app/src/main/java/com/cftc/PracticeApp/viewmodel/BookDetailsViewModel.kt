package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.BookDetailsRepository
import com.cftc.PracticeApp.ui.BookDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel() : ViewModel() {

    val repo = BookDetailsRepository()

    private val _state = MutableStateFlow<BookDetailsUiState>(BookDetailsUiState())
    val state: StateFlow<BookDetailsUiState> = _state

    fun getBookDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingDetails = true) }
            val bookDetails = repo.getBookDetails()
            _state.update { it.copy(bookDetails = bookDetails, isLoadingDetails = false) }
        }
    }

}