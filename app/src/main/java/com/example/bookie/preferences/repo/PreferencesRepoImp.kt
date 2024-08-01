package com.example.bookie.preferences.repo

import com.example.bookie.network.RemoteDataSource

class PreferencesRepoImp(var remoteDataSource: RemoteDataSource): PreferencesRepo
{
  /*override suspend fun getBooksCategories(): List<String> {
         return remoteDataSource.getBooksCategories()
    }*/
}