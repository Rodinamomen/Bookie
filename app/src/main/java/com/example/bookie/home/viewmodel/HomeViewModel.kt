package com.example.bookie.home.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.bookie.home.repo.HomeRepo
import com.example.bookie.network.model.BookResponse
import com.example.bookie.network.model.Item
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepo: HomeRepo):ViewModel() {
    private val _booksList = MutableLiveData<BookResponse>()
    val booksList: LiveData<BookResponse> = _booksList

    private val _pagedBooks = MutableLiveData<PagingData<Item>>()
    val pagedBooks: LiveData<PagingData<Item>> = _pagedBooks

    fun getBooksFromRemote(){
        viewModelScope.launch {
           val result= homeRepo.getBooksFromRemote()
            _booksList.value= result
        }
    }

    fun getPagedBooks(){
        viewModelScope.launch {
            homeRepo.getPagedBooks().observeForever {
                _pagedBooks.postValue(it)
            }

        }
    }
}