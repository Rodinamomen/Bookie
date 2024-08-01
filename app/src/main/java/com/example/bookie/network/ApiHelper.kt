package com.example.bookie.network



import com.example.bookie.BuildConfig
import com.google.gson.GsonBuilder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiHelper {
        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
}