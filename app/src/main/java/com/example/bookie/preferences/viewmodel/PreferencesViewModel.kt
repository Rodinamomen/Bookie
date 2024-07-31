package com.example.bookie.preferences.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.network.model.Item
import com.example.bookie.preferences.repo.PreferencesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreferencesViewModel(private val preferencesRepo: PreferencesRepo): ViewModel()  {
    private val _categoriesList = MutableLiveData<List<String>>()
    val categoriesList: LiveData<List<String>> = _categoriesList
   fun getBooksCategories(){
        viewModelScope.launch{
           val result = preferencesRepo.getBooksCategories()
            _categoriesList.value= result
        }
    }
}