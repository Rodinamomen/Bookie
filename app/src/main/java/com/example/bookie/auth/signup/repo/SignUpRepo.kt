package com.example.bookie.auth.signup.repo

import android.content.Intent
import androidx.lifecycle.LiveData
import com.example.bookie.auth.signup.model.User
import com.google.firebase.auth.FirebaseUser

interface SignUpRepo {

    val user: LiveData<FirebaseUser?>

    suspend fun createNewUser(newUser: User, password: String, callback: (Boolean, String?) -> Unit)

    suspend fun handleSignInResult(data: Intent?): FirebaseUser?
    fun getGoogleSignInIntent(): Intent





}