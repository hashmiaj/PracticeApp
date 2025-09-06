package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.network.RecipesRepository
import com.cftc.PracticeApp.ui.RecipesUiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel() : ViewModel() {
    private val _state = MutableStateFlow(RecipesUiState())
    val state: StateFlow<RecipesUiState> = _state.asStateFlow()

    private val repo = RecipesRepository()

    init {
        getAllRecipes()
        observeSearchQuery()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllRecipes()
            _state.update { it.copy(isLoading = false, recipesList = res) }
        }
    }

    fun toggleFavorite(id: String) {
        _state.update { s ->
            s.copy(recipesList = s.recipesList.map { recipe ->
                if (recipe.id == id) recipe.copy(isFavorited = !recipe.isFavorited) else recipe
            })
        }
    }

    fun onClearFilters() {
        _state.update { it.copy(selectedList = emptyList(), inputQuery = "", appliedQuery = "") }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun selectFilterCategory(selected: RecipeCategory) {
        val currentList = state.value.selectedList
        val newList = if (selected in currentList) {
            currentList - selected
        } else {
            currentList + selected
        }
        _state.update { it.copy(selectedList = newList) }
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