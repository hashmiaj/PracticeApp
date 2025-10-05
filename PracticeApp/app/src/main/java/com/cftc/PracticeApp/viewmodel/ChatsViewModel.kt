package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.ChatsRepository
import com.cftc.PracticeApp.ui.ChatsCategory
import com.cftc.PracticeApp.ui.ChatsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatsViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ChatsUiState())
    val state: StateFlow<ChatsUiState> = _state.asStateFlow()

    private val repo = ChatsRepository()

    init {
        getAllChats()
        observeSearchQuery()
    }

    private fun getAllChats() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllChats()
            _state.update { it.copy(isLoading = false, list = res) }
        }
    }

    fun onUpdateSearchQuery(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun onCategorySelected(category: ChatsCategory) {
        _state.update { it.copy(selectedCategory = category) }       // <- no filtering here
    }


    private fun observeSearchQuery() {
        viewModelScope.launch {
            _state
                .mapNotNull { it.inputQuery }
                .debounce(300)
                .distinctUntilChanged()
                .collect { debounce ->
                    _state.update { it.copy(appliedQuery = debounce) }
                }
        }
    }
}