package com.example.bookie.auth.signup.repo

import androidx.lifecycle.LiveData
import com.example.bookie.auth.signup.model.User

interface SignUpRepo {

    suspend fun CreateNewUser(newUser: User, callback: (Boolean, String?) -> Unit)

}