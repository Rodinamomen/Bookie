package com.example.bookie.home.repo

import com.example.bookie.network.RemoteDataSource
import com.example.bookie.network.model.BookResponse


class HomeRepoImp(var remoteDataSource: RemoteDataSource):HomeRepo {
    override suspend fun getBooksFromRemote(): BookResponse {
        return remoteDataSource.getBooksFromRemote()
    }
}