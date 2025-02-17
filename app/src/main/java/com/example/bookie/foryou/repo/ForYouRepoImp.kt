package com.example.bookie.foryou.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bookie.network.BooksPagingSource
import com.example.bookie.network.RemoteDataSource
import com.example.bookie.network.model.Item
import kotlinx.coroutines.flow.Flow

class ForYouRepoImp(val remoteDataSource: RemoteDataSource):ForYouRepo {
    override suspend fun getBooks(query: String): Flow<PagingData<Item>> {
        Log.d("testing", "getBooks:$query")
        return Pager(
                config = PagingConfig(
                    pageSize = 40,
                    enablePlaceholders = false
                ),
        pagingSourceFactory = { BooksPagingSource(remoteDataSource, query) }
        ).flow
    }
}