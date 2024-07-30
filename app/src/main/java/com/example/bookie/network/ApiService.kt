package com.example.bookie.network
import com.example.bookie.network.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/volumes")
    suspend fun getBooksFromRemote(@Query("key") apiKey: String , @Query("q") q:String): BookResponse
}