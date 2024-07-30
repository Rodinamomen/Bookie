package com.example.bookie.network

import com.example.bookie.network.model.BookResponse


interface RemoteDataSource {
    suspend fun getBooksFromRemote(): BookResponse
}