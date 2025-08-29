package com.cftc.PracticeApp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.network.BlogsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlogViewModel() : ViewModel() {
    private val repo: BlogsRepository = BlogsRepository()

    private val _state = MutableStateFlow<BlogsUiState>(BlogsUiState.Initial)
    val state: StateFlow<BlogsUiState> = _state

    private val _effects = Channel<BlogsUiEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    private var page = 1
    private var pageSize = 10
    private var maxPages = 3

    fun loadFirstPage() {
        viewModelScope.launch {
            _state.value = BlogsUiState.Loading
            runCatching { repo.fetchData(page, pageSize) }
                .onSuccess { items ->
                    _state.value = BlogsUiState.Content(
                        items = items,
                        isAppending = false,
                        canLoadMore = page < maxPages,
                    )
                }
                .onFailure {
                    _state.value = BlogsUiState.Error("Failed to load blogs!")
                }
        }
    }

    fun loadNextPage() {
        val current = _state.value
        if (current !is BlogsUiState.Content) return
        if (current.isAppending || !current.canLoadMore) return

        _state.value = current.copy(isAppending = true, appendError = null)

        viewModelScope.launch {
            _state.value = BlogsUiState.Loading
            runCatching {
                page += 1
                repo.fetchData(page, pageSize)
            }.onSuccess { newItems ->
                _state.value = current.copy(
                    items = current.items + newItems,
                    isAppending = false,
                    canLoadMore = page < maxPages,
                    appendError = null
                )
            }.onFailure { e ->
                page =- 1
                _state.value = current.copy(
                    isAppending = false,
                    appendError = e.message ?: "Append failed"
                )
                _effects.trySend(BlogsUiEffect.ShowMessage("Couldn't Load more, sorry!"))
            }
        }
    }

    fun retryInitial() = loadFirstPage()
}