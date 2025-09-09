package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.User
import kotlinx.coroutines.delay

class SaveProfileRepository() {
    suspend fun saveProfile(user: User) {
        delay(2000)
    }
}