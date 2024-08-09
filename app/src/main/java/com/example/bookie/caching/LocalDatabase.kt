package com.example.bookie.caching

import com.example.bookie.network.model.Item

interface LocalDatabase {

    suspend fun insertBook(book: Item)
    suspend fun getBookById(id: String): Item?
    suspend fun getAllBooks(): List<Item>
    suspend fun deleteAllBooks()
}