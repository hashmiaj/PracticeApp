package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.UserModel

data class UserUiState(
    val userData: UserModel? = null,
    val isLoading: Boolean = false,
)