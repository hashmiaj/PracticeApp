package com.cftc.PracticeApp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.network.RecipesRepository
import com.cftc.PracticeApp.ui.RecipesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel() : ViewModel() {
    private val _state = MutableStateFlow(RecipesUiState())
    val state: StateFlow<RecipesUiState> = _state.asStateFlow()

    private val repo = RecipesRepository()

    fun getAllRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllRecipes()
            _state.update { it.copy(isLoading = false, recipesList = res) }
        }
    }

    fun onSelectCategory(selected: RecipeCategory) {
        _state.update { it.copy(selectedCategory = selected) }
    }

}