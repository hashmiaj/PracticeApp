package com.cftc.PracticeApp

data class LoginUiState(
    val emailAddress: String? = null,
    val password: String? = null,
    val isLoading: Boolean = false,
)