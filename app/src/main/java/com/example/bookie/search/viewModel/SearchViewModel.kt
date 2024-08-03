package com.example.bookie.search.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.network.model.BookResponse
import com.example.bookie.search.repo.SearchRepo
import kotlinx.coroutines.launch

class SearchViewModel(val searchRepo: SearchRepo): ViewModel() {

    private var _booksResults = MutableLiveData<BookResponse>()
    val booksResults: LiveData<BookResponse> = _booksResults

    fun searchForBooks(query:String){
        viewModelScope.launch {
            val results = searchRepo.searchForBooks(query)
            _booksResults.value = results
        }
    }


}