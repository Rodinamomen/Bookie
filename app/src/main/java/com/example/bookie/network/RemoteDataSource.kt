package com.example.bookie.network

import com.example.bookie.network.model.BooksResponse


interface RemoteDataSource {
    suspend fun getBooksFromRemote(): BooksResponse
}