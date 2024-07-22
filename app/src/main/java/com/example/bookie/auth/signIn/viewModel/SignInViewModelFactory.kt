package com.example.bookie.auth.signIn.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.auth.signIn.repo.SignInRepo
import com.example.bookie.auth.signup.repo.SignUpRepo

class SignInViewModelFactory(var signInRepo: SignInRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            SignInViewModel(signInRepo) as T
        }else{
            throw IllegalArgumentException("home view model class not found")
        }
    }
}