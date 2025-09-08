package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.BookCategory
import com.cftc.PracticeApp.network.LibraryRepository
import com.cftc.PracticeApp.ui.LibraryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel() : ViewModel() {
    private val _state = MutableStateFlow(LibraryUiState())
    val state: StateFlow<LibraryUiState> = _state.asStateFlow()

    private val repo = LibraryRepository()

    init {
        getAllBooks()
        observeSearchQuery()
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllBooks()
            _state.update { it.copy(isLoading = false, list = res) }
        }
    }

    fun onCategorySelected(selected: BookCategory) {
        _state.update { it.copy(selected = selected) }
    }

    fun onQueryUpdated(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun toggleFavoritesIcon(id: String) {
        _state.update { current ->
            current.copy(
                list = current.list.map { book ->
                    if (book.id == id) book.copy(isFavorited = !book.isFavorited) else book
                }
            )
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _state
                .map { it.inputQuery }
                .debounce(300)
                .distinctUntilChanged()
                .collect { debounce ->
                    _state.update { it.copy(appliedQuery = debounce) }
                }
        }
    }

}