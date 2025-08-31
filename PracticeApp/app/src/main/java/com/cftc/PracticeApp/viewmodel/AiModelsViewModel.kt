package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.AiModel
import com.cftc.PracticeApp.model.ModelCategory
import com.cftc.PracticeApp.network.AiModelRepository
import com.cftc.PracticeApp.ui.AiModelsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiModelsViewModel() : ViewModel() {

    val repo = AiModelRepository()
    private val _state = MutableStateFlow<AiModelsUiState>(AiModelsUiState())
    val state: StateFlow<AiModelsUiState> = _state.asStateFlow()

    fun loadAiModelsList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val res = repo.getAiModelsList()
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
}