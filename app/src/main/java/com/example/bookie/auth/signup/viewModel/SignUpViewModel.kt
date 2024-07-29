package com.example.bookie.auth.signup.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.auth.signup.model.User
import com.example.bookie.auth.signup.repo.SignUpRepo
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SignUpViewModel(var repo: SignUpRepo): ViewModel() {

    private var _isUserCreated = MutableLiveData<Boolean> ()
    val isUserCreated  = _isUserCreated

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user


    fun signUpByEmailAndPassword(user: User, password: String){
        viewModelScope.launch {
            repo.createNewUser(user, password) { isSuccess, errorMessage ->
                _isUserCreated.value = isSuccess
            }

        }
    }

    fun getGoogleSignInIntent(): Intent {
        return repo.getGoogleSignInIntent()
    }

    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            repo.handleSignInResult(data){user, isCreated ,errorMessage ->
                _user.value = user
                _isUserCreated.value = isCreated

            }


        }

    }

}