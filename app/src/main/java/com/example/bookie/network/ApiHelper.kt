package com.example.bookie.network



import com.example.bookie.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiHelper {
    private val loggingInterceptor = Interceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.googleapis.com/books/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
}