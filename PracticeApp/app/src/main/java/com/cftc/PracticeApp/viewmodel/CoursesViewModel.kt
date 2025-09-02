package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.CourseCategory
import com.cftc.PracticeApp.network.CoursesRepository
import com.cftc.PracticeApp.uistate.CoursesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoursesViewModel(): ViewModel() {
    private val repo = CoursesRepository()

    private val _state = MutableStateFlow(CoursesUiState())
    val state : StateFlow<CoursesUiState> = _state.asStateFlow()

    init {
        getAllCourse()
        observeSearchQuery()
    }

    private fun getAllCourse() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllCourses()
            _state.update { it.copy(isLoading = false, list = res) }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun updateSelectedCategory(selected: CourseCategory) {
        _state.update { it.copy(selected = selected) }
    }

    private fun observeSearchQuery() {
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