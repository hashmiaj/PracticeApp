package com.cftc.PracticeApp.ui.BlogsScreen

import com.cftc.PracticeApp.model.BlogPost

sealed interface BlogsUiState {
    data object Loading : BlogsUiState
    data class Content(
        val posts: List<BlogPost>,
        val isRefreshing: Boolean,
        val canLoadMore: Boolean,
        val isLoadingMore: Boolean,
    ) : BlogsUiState
    data class Error(val message: String) : BlogsUiState
    data object Empty : BlogsUiState
}

sealed interface BlogsUiEffect {
    data class ShowMessage(val text: String) : BlogsUiEffect
}
