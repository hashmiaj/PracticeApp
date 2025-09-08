package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.PaymentType
import com.cftc.PracticeApp.model.TransactionType
import com.cftc.PracticeApp.network.TransactionRepository
import com.cftc.PracticeApp.ui.TransactionsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel() : ViewModel() {
    private val _state = MutableStateFlow(TransactionsUiState())
    val state: StateFlow<TransactionsUiState> = _state.asStateFlow()

    private val repo = TransactionRepository()

    init {
        getAllTransactions()
        observeSearchQuery()
    }

    fun getAllTransactions() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getAllTransactions()
            _state.update { it.copy(isLoading = false, list = res) }
        }
    }

    fun onClearFilters() {
        _state.update {
            it.copy(
                selectedTransactionType = emptyList(),
                appliedQuery = "",
                inputQuery = "",
                selectedPaymentType = PaymentType.All
            )
        }
    }

    fun onInputQueryValueChanged(input: String) {
        _state.update { it.copy(inputQuery = input) }
    }

    fun onPaymentTypeValueChange(type: PaymentType) {
        _state.update { it.copy(selectedPaymentType = type) }
    }

    fun toggleTransactionType(type: TransactionType) {
        val currentList = _state.value.selectedTransactionType
        if (currentList.contains(type)) {
            _state.update { it.copy(selectedTransactionType = currentList - type) }
        } else {
            _state.update { it.copy(selectedTransactionType = currentList + type) }
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