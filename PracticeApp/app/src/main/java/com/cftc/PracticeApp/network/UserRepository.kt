package com.cftc.PracticeApp.network

import com.cftc.PracticeApp.model.UserModel
import kotlinx.coroutines.delay

class UserRepository() {
    suspend fun getUserDetails() : UserModel {
        delay(2000)
        return UserModel(
            id = "1",
            imageUrl = "https://media.licdn.com/dms/image/v2/C4D03AQG51giAcHEYjw/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1632192004998?e=1759363200&v=beta&t=BWjFRol25WodPDZB3dlIOLzI3Z_R4DBCmSZcIGfi_dY",
            name = "Abdullah Hashmi",
            emailAddress = "abdullah.hashmi21@gmail.com"
        )
    }
}