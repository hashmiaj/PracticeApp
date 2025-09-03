package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.DishCategory
import com.cftc.PracticeApp.network.DishesRepository
import com.cftc.PracticeApp.uistate.DishesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DishesViewModel() : ViewModel() {
    private val _state = MutableStateFlow(DishesUiState())
    val state: StateFlow<DishesUiState> = _state.asStateFlow()

    private val repo = DishesRepository()

    init {
        getAllDishes()
    }

    private fun getAllDishes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllDishes()
            _state.update { it.copy(isLoading = false, dishesList = res) }
        }
    }

    fun selectTab(selected: DishCategory) {
        _state.update { it.copy(selectedCategory = selected) }
    }

}