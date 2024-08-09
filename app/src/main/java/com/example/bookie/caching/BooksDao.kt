package com.example.bookie.caching

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookie.network.model.Item

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Item)

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): Item?

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Item>

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}