package com.cftc.PracticeApp.ui

import com.cftc.PracticeApp.model.User

data class SaveProfileUiState(
    val user: User? = null,
    val isSavingProfile: Boolean = false,
)