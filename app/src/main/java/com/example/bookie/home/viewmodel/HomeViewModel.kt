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

    private val _pagedBooks = MutableLiveData<PagingData<Item>>()
    val pagedBooks: LiveData<PagingData<Item>> = _pagedBooks

    private val _cachedBooks = MutableLiveData<List<Item>>()
    val cachedBooks: LiveData<List<Item>> = _cachedBooks


    fun getPagedBooks(){
        viewModelScope.launch {
            homeRepo.getPagedBooks().observeForever {
                _pagedBooks.postValue(it)
            }

        }
    }

    fun getAllBooks(){
        viewModelScope.launch {
            _cachedBooks.value = homeRepo.getAllBooks()
        }
    }

    fun insertBook(book: Item){
        viewModelScope.launch {
            homeRepo.insertBook(book)
        }
    }

    fun deleteAllBooks(){
        viewModelScope.launch {
            homeRepo.deleteAllBooks()
        }
    }

    fun getBookById(book: Item){
        viewModelScope.launch {
            homeRepo.insertBook(book)
        }
    }


}