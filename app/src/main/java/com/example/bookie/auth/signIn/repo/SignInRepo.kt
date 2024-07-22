package com.example.bookie.auth.signIn.repo

import com.example.bookie.auth.signup.model.User

interface SignInRepo {

    suspend fun singInWithEmailAndPassword(email:String, password:String, callback: (Boolean, String?) -> Unit)
}