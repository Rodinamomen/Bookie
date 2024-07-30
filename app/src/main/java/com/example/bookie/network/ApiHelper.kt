package com.example.bookie.network



import com.example.bookie.BuildConfig
import com.google.gson.GsonBuilder

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiHelper {
        val gson = GsonBuilder().serializeNulls().create()
        val retrofit = Retrofit.Builder()
            //https://api.nytimes.com/svc/books/v3/lists.json/current/hardcover-fiction.json/?api-key=${BuildConfig.api_key}
            .baseUrl("https://www.googleapis.com/books/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
}