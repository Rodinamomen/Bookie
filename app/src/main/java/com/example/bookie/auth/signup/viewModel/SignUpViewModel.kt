package com.example.bookie.auth.signup.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.auth.signup.model.User
import com.example.bookie.auth.signup.repo.SignUpRepo
import com.example.bookie.network.model.BooksResponse
import kotlinx.coroutines.launch

class SignUpViewModel(var repo: SignUpRepo): ViewModel() {

    private var _isUserCreated = MutableLiveData<Boolean> ()
    val isUserCreated  = _isUserCreated

    fun signUpByEmailAndPassword(user: User){
        viewModelScope.launch {
            repo.CreateNewUser(user) { isSuccess, errorMessage ->
                _isUserCreated.value = isSuccess
            }

        }
    }


}