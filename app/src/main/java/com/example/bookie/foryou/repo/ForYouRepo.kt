package com.example.bookie.foryou.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.bookie.network.model.Item
import kotlinx.coroutines.flow.Flow

interface ForYouRepo {
    suspend fun getBooks(query: String): Flow<PagingData<Item>>
}