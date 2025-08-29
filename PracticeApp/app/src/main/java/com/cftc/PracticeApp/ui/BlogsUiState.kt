package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.BlogPost

sealed interface BlogsUiState {
    data object Initial : BlogsUiState
    data object Loading : BlogsUiState
    data class Content(
        val items: List<BlogPost>,
        val isAppending: Boolean,
        val canLoadMore: Boolean,
        val appendError: String? = null
    ) : BlogsUiState
    data class Error(val message: String) : BlogsUiState
}

sealed interface BlogsUiEffect {
    data class ShowMessage(val message: String) : BlogsUiEffect
}