package com.example.bookie.home.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bookie.caching.LocalDatabase
import com.example.bookie.network.BooksPagingSource
import com.example.bookie.network.RemoteDataSource
import com.example.bookie.network.model.BookResponse
import com.example.bookie.network.model.Item


class HomeRepoImp(var remoteDataSource: RemoteDataSource, val localDataSource: LocalDatabase):HomeRepo {
    override suspend fun getBooksFromRemote(): BookResponse {
        val randomLetters = ('A'..'Z') + ('a'..'z')
        return remoteDataSource.getBooksFromRemote(0, randomLetters.random().toString())
    }

    override suspend fun getPagedBooks(): LiveData<PagingData<Item>> {
        val randomLetters = ('A'..'Z') + ('a'..'z')
        return Pager(
            config = PagingConfig(
                pageSize = 40,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BooksPagingSource(remoteDataSource, randomLetters.random().toString()) }
        ).liveData
    }

    override suspend fun insertBook(book: Item){
        localDataSource.insertBook(book)
    }
    override suspend fun getBookById(id: String): Item?{
        return localDataSource.getBookById(id)
    }
    override suspend fun getAllBooks(): List<Item>{
        return localDataSource.getAllBooks()
    }
    override suspend fun deleteAllBooks(){
        localDataSource.deleteAllBooks()
    }
}