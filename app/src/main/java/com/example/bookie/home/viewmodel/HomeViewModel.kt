package com.example.bookie.home.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookie.home.repo.HomeRepo
import com.example.bookie.network.model.Doc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val homeRepo: HomeRepo):ViewModel() {
    private val _booksList = MutableLiveData<List<Doc>>()
    val booksList: LiveData<List<Doc>> = _booksList

    fun getBooksFromRemote(){
        viewModelScope.launch {
           val result= homeRepo.getBooksFromRemote().docs
            _booksList.value= result
        }
    }
}