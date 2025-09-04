package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.RecipeCategory
import com.cftc.PracticeApp.network.RecipesRepository
import com.cftc.PracticeApp.ui.RecipesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel() : ViewModel() {
    private val _state = MutableStateFlow(RecipesUiState())
    val state: StateFlow<RecipesUiState> = _state.asStateFlow()


    private val repo = RecipesRepository()


    init {
        getAllRecipes()
        observeInputQuery()
    }

    fun getAllRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllRecipes()
            _state.update { it.copy(isLoading = false, recipesList = res) }
        }
    }

    fun toggleFavorite(id: String) {
        val updated = _state.value.recipesList.map { recipe ->
            if (recipe.id == id) recipe.copy(isFavorited = !recipe.isFavorited) else recipe
        }
        _state.update { it.copy(recipesList = updated) }
    }

    fun selectCategory(selected: RecipeCategory) {
        _state.update { it.copy(selectedCategory = selected) }
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun observeInputQuery() {
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