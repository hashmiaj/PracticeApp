package com.cftc.PracticeApp

import kotlinx.coroutines.delay

class LoginRepository() {
    suspend fun loginUser(emailAddress: String, password: String) : String {
        delay(4000)
        return "Successful Login"
    }
}