package com.example.bookie.network

import android.annotation.SuppressLint
import android.util.Log
import com.example.bookie.BuildConfig
import com.example.bookie.network.model.BookResponse
import retrofit2.HttpException


object ApiClient:RemoteDataSource {

   /* override suspend fun getBooksFromRemote(): BookResponse {
        Log.d("ApiClient", "Request URL: https://www.googleapis.com/books/v1/volumes?key=${BuildConfig.key}&q=\"''\"  ")
      val response=   ApiHelper.retrofit.create(ApiService::class.java).getBooksFromRemote(apiKey = BuildConfig.key,q="" )
        Log.d("hello", "getBooksFromRemote:${response.items} ")
        return response
    }*/
    override suspend fun getBooksFromRemote(startIndex:Int?,query:String): BookResponse {
        Log.d("ApiClient", "Request URL: https://www.googleapis.com/books/v1/volumes?key=${BuildConfig.key}&q=$query&startIndex=${startIndex}")
        val response = ApiHelper.retrofit.create(ApiService::class.java)
            .getBooksFromRemote(
                apiKey = BuildConfig.key,
                q = query,
                startIndex = 0
            )
        Log.d("ApiClient", "Response: ${response.items}")
        return response
    }


    /*   override suspend fun getBooksCategories(): List<String> {
         val categoriesSet = mutableSetOf<String>()
         var startIndex = 0
         val maxResults = 40 // Maximum number of results per request
         var totalItems: Int
         do {
             try {
                 val response = ApiHelper.retrofit.create(ApiService::class.java)
                     .getBooksFromRemote(apiKey = BuildConfig.key, q = "subject:", startIndex = startIndex, maxResults = maxResults)

                 totalItems = response.items.size
                 response.items?.forEach { item ->
                     item.volumeInfo.categories?.let { categoriesSet.addAll(it) }
                 }
                 startIndex += maxResults
             } catch (e: HttpException) {
                 Log.e("PreferencesRepo", "HTTP Exception: ${e.message()}", e)
                 break
             } catch (e: Exception) {
                 Log.e("PreferencesRepo", "Exception: ${e.message}", e)
                 break
             }
         } while (startIndex < totalItems)

         return categoriesSet.toList()
     }*/
}