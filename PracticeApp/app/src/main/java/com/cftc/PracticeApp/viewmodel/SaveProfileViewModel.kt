package com.cftc.PracticeApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cftc.PracticeApp.model.User
import com.cftc.PracticeApp.network.SaveProfileRepository
import com.cftc.PracticeApp.ui.SaveProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SaveProfileViewModel() : ViewModel() {
    private val _state = MutableStateFlow(SaveProfileUiState())
    val state: StateFlow<SaveProfileUiState> = _state.asStateFlow()

    private val repo = SaveProfileRepository()


    fun saveProfile(user: User) {
        viewModelScope.launch {
            _state.update { it.copy(isSavingProfile = true) }
            repo.saveProfile(user)
            _state.update { it.copy(isSavingProfile = false, user = user) }
        }
    }
}