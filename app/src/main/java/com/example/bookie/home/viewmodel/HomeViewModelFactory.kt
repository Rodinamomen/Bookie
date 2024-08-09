package com.example.bookie.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.home.repo.HomeRepo

class HomeViewModelFactory (var homeRepo: HomeRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(homeRepo) as T
        }else{
            throw IllegalArgumentException("home view model class not found")
        }
    }
}