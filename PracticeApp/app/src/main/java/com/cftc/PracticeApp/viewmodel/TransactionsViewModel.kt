package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.TransactionType
import com.cftc.PracticeApp.network.TransactionsRepository
import com.cftc.PracticeApp.ui.TransactionsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel() : ViewModel() {
    private val _state = MutableStateFlow(TransactionsUiState())
    val state: StateFlow<TransactionsUiState> = _state.asStateFlow()

    private val repo = TransactionsRepository()

    init {
        getAllTransactions()
        observerSearchQuery()
    }

    private fun getAllTransactions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllTransactions()
            _state.update { it.copy(isLoading = false, transactionsList = res) }
        }
    }

    fun onSelectType(selected: TransactionType) {
        _state.update { it.copy(selectedType = selected) }
    }

    fun onInputQueryChanged(query: String) {
        _state.update { it.copy(inputQuery = query) }
    }

    fun resetFilters() {
        _state.update {
            it.copy(
                inputQuery = "",
                appliedQuery = "",
                selectedType = TransactionType.All
            )
        }
    }

    private fun observerSearchQuery() {
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