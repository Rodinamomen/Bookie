package com.example.bookie

import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import com.example.bookie.home.repo.HomeRepoImp
import com.example.bookie.home.viewmodel.HomeViewModel
import com.example.bookie.network.ApiClient

class MainActivity : AppCompatActivity() {
    private lateinit var homeViewModel :HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = HomeViewModel(HomeRepoImp(ApiClient))
        homeViewModel.getBooksFromRemote()
        Log.d("testing log", "onViewCreated:$ ")
        homeViewModel.booksList.observe(this){
            Log.d("testing log", "onViewCreated:${it} ")
        }
    }
}