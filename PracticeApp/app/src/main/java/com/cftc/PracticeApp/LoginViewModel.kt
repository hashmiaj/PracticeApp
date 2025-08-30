package com.cftc.PracticeApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val repo = LoginRepository()

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun loginUser(emailAddress: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(emailAddress = emailAddress, password = password, isLoading = true)
            }

            when (val res = repo.loginUser(emailAddress, password)) { }

            _state.update {
                it.copy(isLoading = false)
            }
        }
    }
}