package com.example.bookie.foryou.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bookie.foryou.repo.ForYouRepo
import com.example.bookie.network.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
class ForYouViewModel(val forYouRepo: ForYouRepo):ViewModel() {
     private val _userSelectedItems= MutableLiveData<List<String>>()
    val userSelectedItems:LiveData<List<String>> = _userSelectedItems
    private val _books = MutableLiveData<PagingData<Item>>()
    val books: LiveData<PagingData<Item>> = _books
    private var selectedItems = mutableListOf<String>()
    val auth =FirebaseAuth.getInstance()
    val db =Firebase.firestore
    val userId = auth.currentUser?.uid.toString()
    fun getUserPreferences(){
         viewModelScope.launch {
             Log.d("testing", "getUserPreferences: ")
             db.collection("users").document(userId).collection("Preferences").get().addOnSuccessListener {
                 for(doc in it){
                     selectedItems.add(doc.data["preference"].toString())
                 }
                 _userSelectedItems.postValue(selectedItems)
             }.addOnFailureListener {
                 Log.d("TAG", "getUserPreferences: !!!!")
             }

         }
    }
    fun getBooks(query: String) {
        viewModelScope.launch {
            forYouRepo.getBooks(query)
                .cachedIn(viewModelScope)
                .collect{ pagingData ->
                    _books.value=pagingData
                }
        }
    }
}