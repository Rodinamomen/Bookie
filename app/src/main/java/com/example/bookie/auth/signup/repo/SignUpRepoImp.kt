package com.example.bookie.auth.signup.repo

import android.util.Log
import com.example.bookie.auth.signup.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpRepoImp: SignUpRepo {
    private val TAG = "SignUpRepoImp"
    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    override suspend fun CreateNewUser(newUser: User, callback: (Boolean, String?) -> Unit) {
         auth.createUserWithEmailAndPassword(newUser.email, newUser.password)
            .addOnSuccessListener { authResults ->
                val userCreated = authResults.user
                val newUserHashmap = hashMapOf(
                    "name" to newUser.name,
                    "email" to newUser.email,
                    "password" to newUser.password
                )
                fireStore.collection("users")
                    .document(userCreated!!.uid)
                    .set(newUserHashmap).addOnSuccessListener {
                        Log.d(TAG, "CreateNewUser: the user is added successfully.")
                        callback(true, null)
                    }
                    .addOnFailureListener { error ->
                        Log.d(TAG, "CreateNewUser: failed to create new user, error ${error.message}")
                        callback(false, error.message)
                    }

                return@addOnSuccessListener

            }.addOnFailureListener {error->
                 callback(false, error.message)
                return@addOnFailureListener
             }
    }

}