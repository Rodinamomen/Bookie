package com.example.bookie.foryou.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bookie.network.BooksPagingSource
import com.example.bookie.network.RemoteDataSource
import com.example.bookie.network.model.Item

class ForYouRepoImp(val remoteDataSource: RemoteDataSource):ForYouRepo {
    override suspend fun getBooks(query: String): LiveData<PagingData<Item>> {
        return Pager(
                config = PagingConfig(
                    pageSize = 40,
                    enablePlaceholders = false
                ),
        pagingSourceFactory = { BooksPagingSource(remoteDataSource, query) }
        ).liveData
    }
}