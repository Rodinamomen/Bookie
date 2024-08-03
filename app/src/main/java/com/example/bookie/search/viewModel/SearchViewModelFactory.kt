package com.example.bookie.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.search.repo.SearchRepo

class SearchViewModelFactory(var searchRepo: SearchRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(searchRepo) as T
        }else{
            throw IllegalArgumentException("home view model class not found")
        }
    }
}