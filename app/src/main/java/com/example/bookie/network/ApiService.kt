package com.example.bookie.network
import com.example.bookie.BuildConfig
import com.example.bookie.network.model.BookRespone
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/volumes")
    suspend fun getBooksFromRemote(@Query("key") apiKey: String , @Query("q") q:String): BookRespone
}