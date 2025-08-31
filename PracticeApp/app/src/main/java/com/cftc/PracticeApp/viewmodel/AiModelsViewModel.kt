package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.ModelCategory
import com.cftc.PracticeApp.network.AiModelRepository
import com.cftc.PracticeApp.ui.AiModelsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiModelsViewModel(): ViewModel() {

    private val repo = AiModelRepository()

    private val _state = MutableStateFlow(AiModelsUiState())
    val state: StateFlow<AiModelsUiState> = _state.asStateFlow()

    init {
        loadAiModelsList()
        observeQuery()
    }

    fun loadAiModelsList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            runCatching { repo.getAiModelsList() }
                .onSuccess { list ->
                    _state.update { it.copy(items = list, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e) }
                }
        }
    }

    fun selectCategory(category: ModelCategory) {
        _state.update { it.copy(selected = category) }
    }

    fun onQueryChange(q: String) {
        _state.update { it.copy(inputQuery = q) }   // immediate echo to UI
        // appliedQuery will be set by observeQuery after debounce
    }

    fun resetFilters() {
        _state.update { it.copy(selected = ModelCategory.All, inputQuery = "", appliedQuery = "") }
    }

    private fun observeQuery() = viewModelScope.launch {
        state
            .map { it.inputQuery }
            .debounce(300)             // adjust to taste
            .distinctUntilChanged()
            .collect { debounced ->
                _state.update { it.copy(appliedQuery = debounced) }
            }
    }
}