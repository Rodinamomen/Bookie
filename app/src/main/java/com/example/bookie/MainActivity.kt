package com.example.bookie

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.example.bookie.home.repo.HomeRepoImp
import com.example.bookie.home.viewmodel.HomeViewModel
import com.example.bookie.network.ApiClient

class MainActivity : AppCompatActivity() {
//    private lateinit var homeViewModel :HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        homeViewModel = HomeViewModel(HomeRepoImp(ApiClient))
//        homeViewModel.getBooksFromRemote()
//        Log.d("testing log", "onViewCreated:$ ")
//        homeViewModel.booksList.observe(this){
//            Log.d("testing log", "onViewCreated:${it} ")
//        }
    }
}