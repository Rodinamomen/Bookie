package com.example.bookie.caching

import android.content.Context
import com.example.bookie.network.model.Item

class LocalDatabaseImp(val context: Context): LocalDatabase {

    private var daoBooks: BooksDao

    init {
        val db = BooksDatabase.getInstance(context)
        daoBooks = db.getBooksDao()
    }

    override suspend fun insertBook(book: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookById(id: String): Item? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllBooks(): List<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllBooks() {
        TODO("Not yet implemented")
    }
}