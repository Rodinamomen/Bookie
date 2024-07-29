package com.example.bookie.auth.signup.repo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookie.auth.signup.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SignUpRepoImp(val context: Context): SignUpRepo {
    private val TAG = "SignUpRepoImp"
    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private var googleSignInClient: GoogleSignInClient

    private val _user = MutableLiveData<FirebaseUser?>()
    override val user: LiveData<FirebaseUser?> get() = _user

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("465053659588-aa279fbvhh4coiimtqhr09b349tsr0g9.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
    }

    override suspend fun createNewUser(newUser: User, password: String, callback: (Boolean, String?) -> Unit) {
         auth.createUserWithEmailAndPassword(newUser.email, password)
            .addOnSuccessListener { authResults ->
                val userCreated = authResults.user
                val newUserHashmap = hashMapOf(
                    "name" to newUser.name,
                    "email" to newUser.email,
                    "password" to password
                )
                fireStore.collection("users")
                    .document(userCreated!!.uid)
                    .set(newUserHashmap).addOnSuccessListener {
                        Log.d(TAG, "createNewUser: the user is added successfully.")
                        callback(true, null)
                    }
                    .addOnFailureListener { error ->
                        Log.d(TAG, "createNewUser: failed to create new user, error ${error.message}")
                        callback(false, error.message)
                    }

                return@addOnSuccessListener

            }.addOnFailureListener {error->
                 callback(false, error.message)
                return@addOnFailureListener
             }
    }

    override suspend fun handleSignInResult(data: Intent?, callback: (FirebaseUser?,Boolean, String?) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
             try {
                val account = task.getResult(ApiException::class.java)
                val user = firebaseAuthWithGoogle(account)
                user?.let { saveUserToFirestore(it) }
                callback(user,true,null)
            } catch (e: ApiException) {
                 Log.w(TAG, "Google sign in failed", e)
                 callback(null,false,null)
            }
    }

    override fun getGoogleSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            authResult.user
        } catch (e: Exception) {
            Log.w(TAG, "signInWithCredential:failure", e)
            null
        }
    }

    private suspend fun saveUserToFirestore(user: FirebaseUser) {
        val userMap = hashMapOf(
            "name" to user.displayName,
            "email" to user.email
        )
        try {
            fireStore.collection("users").document(user.uid).set(userMap).await()
        } catch (e: Exception) {
            Log.w(TAG, "Error saving user to Firestore", e)
        }
    }

}