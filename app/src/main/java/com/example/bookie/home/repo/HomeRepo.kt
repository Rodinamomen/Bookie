package com.example.bookie.home.repo

import com.example.bookie.network.model.BookResponse

interface HomeRepo {
   suspend fun getBooksFromRemote(): BookResponse
}