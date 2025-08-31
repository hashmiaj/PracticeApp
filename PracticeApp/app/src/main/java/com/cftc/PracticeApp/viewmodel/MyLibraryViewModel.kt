package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.MyLibraryRepository
import com.cftc.PracticeApp.ui.MyLibraryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyLibraryViewModel() : ViewModel() {
    private val repo = MyLibraryRepository()

    private val _state = MutableStateFlow(MyLibraryUiState())
    val state: StateFlow<MyLibraryUiState> = _state

    fun getMyLibraryBooks() {
        viewModelScope.launch {
            val currentPageNumber = 1
            val pageSize = 10
            _state.update { it.copy(isLoadingBooks = true) }
            val res = repo.getMyLibraryBooks(currentPageNumber, pageSize)
            _state.update {
                it.copy(booksList = res, pageNumber = currentPageNumber, isLoadingBooks = false)
            }
        }
    }

}