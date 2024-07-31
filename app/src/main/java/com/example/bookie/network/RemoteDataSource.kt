package com.example.bookie.network

import com.example.bookie.network.model.BookResponse


interface RemoteDataSource {
    suspend fun getBooksFromRemote(startIndex:Int?,query:String): BookResponse
  //  suspend fun getBooksCategories():List<String>
}