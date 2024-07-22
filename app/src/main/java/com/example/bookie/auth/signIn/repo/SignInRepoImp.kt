package com.example.bookie.auth.signIn.repo

import android.util.Log
import com.example.bookie.auth.signup.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignInRepoImp:SignInRepo {
    private val TAG = "SignInRepoImp"
    private val auth = FirebaseAuth.getInstance()


    override suspend fun singInWithEmailAndPassword(email:String, password:String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d(TAG, "singIn: logged in!")
            callback(true, null)
        }.addOnFailureListener {error->
            Log.d(TAG, "singIn: can't log in, ${error.message}")
            callback(false, error.message)
        }
    }
}