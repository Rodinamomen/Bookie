package com.example.bookie.home.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.bookie.network.model.BookResponse
import com.example.bookie.network.model.Item

interface HomeRepo {
   suspend fun getBooksFromRemote(): BookResponse

   suspend fun getPagedBooks(): LiveData<PagingData<Item>>

   // for caching
   suspend fun insertBook(book: Item)
   suspend fun getBookById(id: String): Item?
   suspend fun getAllBooks(): List<Item>
   suspend fun deleteAllBooks()
}