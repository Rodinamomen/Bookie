package com.example.bookie.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookie.network.model.Item
// PagingSource<key, value>()
// Key is the type of the unique id
// value the return item so in that case we need to return item list that each item has String Id value
class BooksPagingSource(  private val remoteDataSource: RemoteDataSource,private val query:String):PagingSource<Int, Item>() {
   //It is used to get the anchorPosition which basically tells
    // where we are currently present and our logic that how value of page changes when we scrolls.
    //if user refreshed the page
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
      //his is the position of an item in the list that is currently being displayed to the user.
        // When the user scrolls, the anchor position changes to reflect the position of the item currently in view.
        //when a refresh or a new load happens, the library uses the anchor position to determine the closest item that has been successfully loaded and fetches data around this position.
        // so we use thr closespagetopostion to get the closest page to ancherpositon
       return state.anchorPosition?.let { anchorPosition ->
           state.closestPageToPosition(anchorPosition)?.prevKey?.plus(40)
               ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(40)
       }
    }
   //used to define which page you currently requires.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
     return try {
         // 1(start position)
         // start index = 0 if null or the value if not null
         val startIndex = params.key?:0
         // (2)fetching data from remote
         val response = remoteDataSource.getBooksFromRemote(startIndex, query =query )
         // (3) determine next and previous page
         val prev = if (startIndex==0){
             null
         }else{
             startIndex-40
         }
         val next = if(response.items.isEmpty()){
             null
         }else{
             startIndex+40
         }
         // (4) loading page by sending prev and next and results
         LoadResult.Page(response.items,prev,next)
     }catch (e:Exception){
         LoadResult.Error(e)
     }
     }
}