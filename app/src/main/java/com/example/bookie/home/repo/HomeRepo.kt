package com.example.bookie.home.repo

import com.example.bookie.network.model.BooksResponse

interface HomeRepo {
   suspend fun getBooksFromRemote(): BooksResponse
}