package com.cftc.PracticeApp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.ClassCategory
import com.cftc.PracticeApp.network.FitnessClassRepository
import com.cftc.PracticeApp.uistate.FitnessClassUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FitnessClassViewModel() : ViewModel() {
    private val _state = MutableStateFlow(FitnessClassUiState())
    val state: StateFlow<FitnessClassUiState> = _state.asStateFlow()

    private val repo = FitnessClassRepository()

    init {
        getAllClasses()
    }

    private fun getAllClasses() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getClasses()
            _state.update { it.copy(isLoading = false, classList = res) }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val fresh = repo.getClasses()
            _state.update { it.copy(isLoading = false, classList = fresh) }
        }
    }

    fun selectCategory(selected: ClassCategory) {
        _state.update { it.copy(selected = selected) }
    }

    fun toggleJoinClass(id: String) {
        _state.update { s ->
            s.copy(classList = s.classList.map { c ->
                if (c.id == id) c.copy(hasJoined = !c.hasJoined) else c
            })
        }
    }

}