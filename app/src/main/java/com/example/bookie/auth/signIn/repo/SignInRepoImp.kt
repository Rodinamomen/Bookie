package com.example.bookie.auth.signIn.repo

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookie.auth.signup.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class SignInRepoImp(val context: Context):SignInRepo {
    private val TAG = "SignInRepoImp"
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val _user = MutableLiveData<FirebaseUser?>()
    override val user: LiveData<FirebaseUser?> get() = _user


    init {
        Log.d(TAG, "the repo is started")

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("465053659588-aa279fbvhh4coiimtqhr09b349tsr0g9.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(context, gso)
//        googleSignInClient.signOut()
    }

//    private val googleSignInClient by lazy {
//        GoogleSignIn.getClient(context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("YOUR_WEB_CLIENT_ID")
//            .requestEmail()
//            .build())
//    }

    override suspend fun singInWithEmailAndPassword(email:String, password:String, callback: (Boolean, String?) -> Unit) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d(TAG, "singIn: logged in!")
            callback(true, null)
        }.addOnFailureListener {error->
            Log.d(TAG, "singIn: can't log in, ${error.message}")
            callback(false, error.message)
        }
    }

    override suspend fun handleSignInResult(data: Intent?): FirebaseUser? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        return try {
            Log.d(TAG, "handleSignInResult: enter the try for handle Results")
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)
            null
        }
        catch (e: Exception) {
            Log.w(TAG, "Google sign in failed", e)
            null
        }
    }

    override fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("465053659588-aa279fbvhh4coiimtqhr09b349tsr0g9.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
        googleSignInClient.signOut()
       return googleSignInClient.signInIntent
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount?): FirebaseUser? {
        auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            authResult.user
        } catch (e: Exception) {
            Log.w(TAG, "signInWithCredential:failure", e)
            null
        }
    }
}