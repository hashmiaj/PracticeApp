package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cftc.PracticeApp.network.UserRepository
import com.cftc.PracticeApp.ui.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(): ViewModel() {

    private val repo = UserRepository()

    private val _state = MutableStateFlow(UserUiState())
    val state: StateFlow<UserUiState> = _state

    fun getUserDetails() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val res = repo.getUserDetails()
            _state.update { it.copy(userData = res, isLoading = false) }
        }
    }
}