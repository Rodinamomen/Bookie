package com.example.bookie.auth.signIn.repo

import android.content.Intent
import androidx.lifecycle.LiveData
import com.example.bookie.auth.signup.model.User
import com.google.firebase.auth.FirebaseUser

interface SignInRepo {

    val user: LiveData<FirebaseUser?>
    suspend fun singInWithEmailAndPassword(email:String, password:String, callback: (Boolean, String?) -> Unit)
    suspend fun handleSignInResult(data: Intent?): FirebaseUser?
    fun getGoogleSignInIntent(): Intent
}