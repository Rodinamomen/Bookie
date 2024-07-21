package com.example.bookie.network

import com.example.bookie.network.model.BooksResponse


object ApiClient:RemoteDataSource {
    override suspend fun getBooksFromRemote(): BooksResponse {
        return ApiHelper.retrofit.create(ApiService::class.java).getBooksFromRemote()
    }
}