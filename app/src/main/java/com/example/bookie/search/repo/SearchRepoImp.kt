package com.example.bookie.search.repo

import com.example.bookie.network.RemoteDataSource
import com.example.bookie.network.model.BookResponse

class SearchRepoImp(private var remoteDataSource: RemoteDataSource): SearchRepo {

    override suspend fun searchForBooks(query: String): BookResponse {
        return remoteDataSource.getBooksFromRemote(0, query)
    }
}