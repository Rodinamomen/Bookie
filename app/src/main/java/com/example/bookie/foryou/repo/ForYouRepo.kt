package com.example.bookie.foryou.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.bookie.network.model.Item

interface ForYouRepo {
    suspend fun getBooks(query: String): LiveData<PagingData<Item>>
}