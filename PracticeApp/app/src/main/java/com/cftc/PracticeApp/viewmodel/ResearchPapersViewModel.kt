package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.ResearchPaperRepository
import com.cftc.PracticeApp.ui.ResearchPapersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResearchPapersViewModel : ViewModel() {
    private val _state = MutableStateFlow(ResearchPapersUiState())
    val state: StateFlow<ResearchPapersUiState> = _state.asStateFlow()

    private val repo = ResearchPaperRepository()

    init {
        getAllResearchPapers()
        observeSearchQuery()
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun toggleBookmark(id: String) {
        _state.update { s ->
            s.copy(listOfPapers = s.listOfPapers.map { paper ->
                if (paper.id == id) paper.copy(isBookmarked = !paper.isBookmarked) else paper
            })
        }
    }

    private fun getAllResearchPapers() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val list = repo.getAllResearchPapers()
            _state.update { it.copy(isLoading = false, listOfPapers = list) }
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _state
                .mapNotNull { it.inputQuery }
                .debounce(300)
                .distinctUntilChanged()
                .collect { debounce ->
                    _state.update { it.copy(appliedQuery = debounce) }
                }
        }
    }
}