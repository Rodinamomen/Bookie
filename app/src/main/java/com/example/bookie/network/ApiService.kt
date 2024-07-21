package com.example.bookie.network
import com.example.bookie.network.model.BooksResponse
import retrofit2.http.GET

interface ApiService {
    @GET("search.json?q=title:&limit=50")
    suspend fun getBooksFromRemote(): BooksResponse
}