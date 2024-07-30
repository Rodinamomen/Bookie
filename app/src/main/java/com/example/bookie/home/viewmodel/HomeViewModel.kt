package com.example.bookie.home.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.home.repo.HomeRepo
import com.example.bookie.network.model.Item
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepo: HomeRepo):ViewModel() {
    private val _booksList = MutableLiveData<List<Item>>()
    val booksList: LiveData<List<Item>> = _booksList

    fun getBooksFromRemote(){
        viewModelScope.launch {
           val result= homeRepo.getBooksFromRemote().items
            _booksList.value= result
        }
    }
}