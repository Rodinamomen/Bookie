package com.example.bookie

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.example.bookie.home.repo.HomeRepoImp
import com.example.bookie.home.viewmodel.HomeViewModel
import com.example.bookie.network.ApiClient
import com.example.bookie.preferences.repo.PreferencesRepoImp
import com.example.bookie.preferences.viewmodel.PreferencesViewModel

class MainActivity : AppCompatActivity() {
 // private lateinit var homeViewModel :HomeViewModel
//private lateinit var preferencesViewModel: PreferencesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   /*    preferencesViewModel= PreferencesViewModel(PreferencesRepoImp(ApiClient))
        preferencesViewModel.getBooksCategories()
        preferencesViewModel.categoriesList.observe(this){
            Log.d("testing log", "onCreate:$it ")
        }*/
  /*   homeViewModel = HomeViewModel(HomeRepoImp(ApiClient))
   homeViewModel.getBooksFromRemote()
    Log.d("testing log", "onViewCreated:$ ")
   homeViewModel.booksList.observe(this){
       Log.d("testing log", "onViewCreated:${it} ")
    }*/

    }
}