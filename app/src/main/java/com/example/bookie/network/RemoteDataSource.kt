package com.example.bookie.network

import com.example.bookie.network.model.BookRespone


interface RemoteDataSource {
    suspend fun getBooksFromRemote(): BookRespone
}