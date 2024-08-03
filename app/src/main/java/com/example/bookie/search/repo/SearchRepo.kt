package com.example.bookie.search.repo

import com.example.bookie.network.model.BookResponse

interface SearchRepo {

    suspend fun searchForBooks(query: String): BookResponse
}