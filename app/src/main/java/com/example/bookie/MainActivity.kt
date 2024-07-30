package com.example.bookie

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.example.bookie.home.repo.HomeRepoImp
import com.example.bookie.home.viewmodel.HomeViewModel
import com.example.bookie.network.ApiClient

class MainActivity : AppCompatActivity() {
    private lateinit var homeViewModel :HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeViewModel = HomeViewModel(HomeRepoImp(ApiClient))
        homeViewModel.getBooksFromRemote()
        Log.d("testing log", "onViewCreated:$ ")
        homeViewModel.booksList.observe(this){
            books ->
            Log.d("testing log", "onViewCreated:${books[1]} ")
        }
//        val navController = findNavController(R.id.app_navigation)

    }
}