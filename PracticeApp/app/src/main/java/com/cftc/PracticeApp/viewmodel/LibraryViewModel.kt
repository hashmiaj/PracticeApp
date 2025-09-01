package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.LibraryRepository
import com.cftc.PracticeApp.ui.LibraryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.Query

class LibraryViewModel() : ViewModel() {
    private val _state = MutableStateFlow(LibraryUiState())
    val state: StateFlow<LibraryUiState> = _state.asStateFlow()

    init {
        getLibraryBooks()
        observerSearchQuery()
    }

    fun getLibraryBooks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = LibraryRepository.getLibraryBooks()
            _state.update { it.copy(isLoading = false, list = res) }
        }
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun observerSearchQuery() {
        viewModelScope.launch {
            _state
                .mapNotNull { it.inputQuery }
                .distinctUntilChanged()
                .debounce(300)
                .collect { debounce ->
                    _state.update { it.copy(appliedQuery = debounce) }
                }
        }
    }

}