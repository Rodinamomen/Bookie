package com.example.bookie.auth.signup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.auth.signup.repo.SignUpRepo

class SignUpViewModelFactory(var signUpRepo: SignUpRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            SignUpViewModel(signUpRepo) as T
        }else{
            throw IllegalArgumentException("home view model class not found")
        }
    }
}