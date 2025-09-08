package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.FlightStatus
import com.cftc.PracticeApp.network.ArrivalsRepository
import com.cftc.PracticeApp.ui.ArrivalsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArrivalsViewModel() : ViewModel() {
    private val _state = MutableStateFlow(ArrivalsUiState())
    val state: StateFlow<ArrivalsUiState> = _state.asStateFlow()

    private val repo = ArrivalsRepository()

    init {
        getAllArrivals()
        observeSearchQuery()
    }

    private fun getAllArrivals() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllArrivals()
            _state.update { it.copy(isLoading = false, arrivalsList = res) }
        }
    }

    fun onFlightStatusSelected(selected: FlightStatus) {
        _state.update { it.copy(selected = selected) }
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun observeSearchQuery() {
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