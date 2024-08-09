package com.example.bookie.caching

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookie.network.model.Item

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class BooksDatabase: RoomDatabase() {

    abstract fun getBooksDao() : BooksDao

    companion object{
        @Volatile
        private var INSTANCE: BooksDatabase? = null
        fun getInstance(context: Context): BooksDatabase{
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BooksDatabase::class.java,
                    "books_database",
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}