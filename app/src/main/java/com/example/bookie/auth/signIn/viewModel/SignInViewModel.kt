package com.example.bookie.auth.signIn.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.auth.signIn.repo.SignInRepo
import com.example.bookie.auth.signup.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class SignInViewModel(var repo: SignInRepo): ViewModel() {

    private val TAG = "SignInViewModel"
    private var _isUserLoggedIn = MutableLiveData<Boolean> ()
    val isUserLoggedIn  = _isUserLoggedIn

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    fun signInWithEmailAndPassword(email:String, password:String){
        viewModelScope.launch {
            repo.singInWithEmailAndPassword(email, password){isSuccess, errorMessage ->
                _isUserLoggedIn.value = isSuccess
            }
        }

    }

    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "handleSignInResult: point 1")
                val firebaseUser = repo.handleSignInResult(data)
                _user.value = firebaseUser
            }catch (e: TimeoutException){
                Log.d(TAG, "handleSignInResult: point 2")
            }
        }
    }

    fun getGoogleSignInIntent(): Intent {
        Log.d(TAG, "handleSignInResult: point 3")
        return repo.getGoogleSignInIntent()
    }



}