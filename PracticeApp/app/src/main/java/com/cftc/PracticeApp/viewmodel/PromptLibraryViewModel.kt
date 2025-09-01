package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.PromptCategory
import com.cftc.PracticeApp.network.PromptRepository
import com.cftc.PracticeApp.ui.PromptsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PromptLibraryViewModel() : ViewModel() {

    private val repo = PromptRepository()

    private val _state = MutableStateFlow(PromptsUiState())
    val state : StateFlow<PromptsUiState> = _state.asStateFlow()


    init {
        getInitialPrompts()
        observeSearchQuery()
    }

    fun getInitialPrompts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val list = repo.getPrompts()
            _state.update { it.copy(promptsList = list, isLoading = false) }
        }
    }

    fun selectCategory(category: PromptCategory) {
        _state.update { it.copy(selected = category) }
    }

    fun onQueryChange(q: String) {
        _state.update { it.copy(inputQuery = q) }
    }

    fun resetFilters() {
        _state.update { it.copy(selected = PromptCategory.All, inputQuery = "", appliedQuery = "") }
    }

    private fun observeSearchQuery() = viewModelScope.launch {
        state
            .map { it.inputQuery }
            .debounce(300)
            .distinctUntilChanged()
            .collect { debounce ->
                _state.update { it.copy(appliedQuery = debounce) }
            }
    }
}