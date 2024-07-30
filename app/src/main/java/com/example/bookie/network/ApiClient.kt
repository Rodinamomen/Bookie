package com.example.bookie.network

import android.annotation.SuppressLint
import android.util.Log
import com.example.bookie.BuildConfig
import com.example.bookie.network.model.BookResponse


object ApiClient:RemoteDataSource {
    @SuppressLint("SuspiciousIndentation")
    override suspend fun getBooksFromRemote(): BookResponse {
        Log.d("ApiClient", "Request URL: https://www.googleapis.com/books/v1/volumes?key=${BuildConfig.key}&q=\"''\"  ")
      val response=   ApiHelper.retrofit.create(ApiService::class.java).getBooksFromRemote(apiKey = BuildConfig.key,q="''" )
        Log.d("response", "getBooksFromRemote:$response ")
        return response

    }

}