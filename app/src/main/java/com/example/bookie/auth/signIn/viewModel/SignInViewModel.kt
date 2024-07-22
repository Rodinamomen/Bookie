package com.example.bookie.auth.signIn.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.auth.signIn.repo.SignInRepo
import com.example.bookie.auth.signup.model.User
import kotlinx.coroutines.launch

class SignInViewModel(var repo: SignInRepo): ViewModel() {

    private var _isUserLoggedIn = MutableLiveData<Boolean> ()
    val isUserLoggedIn  = _isUserLoggedIn

    fun signInWithEmailAndPassword(email:String, password:String){
        viewModelScope.launch {
            repo.singInWithEmailAndPassword(email, password){isSuccess, errorMessage ->
                _isUserLoggedIn.value = isSuccess
            }
        }

    }

}