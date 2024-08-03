package com.example.bookie.foryou.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.auth.signIn.viewModel.SignInViewModel
import com.example.bookie.foryou.repo.ForYouRepo

class ForYouViewModelFactory(var forYouRepo: ForYouRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ForYouViewModel::class.java)) {
            ForYouViewModel(forYouRepo) as T
        }else{
            throw IllegalArgumentException("ForYouViewModel is not found")
        }
    }
}