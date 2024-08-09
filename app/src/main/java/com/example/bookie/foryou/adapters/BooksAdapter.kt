package com.example.bookie.foryou.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.foryou.adapters.TestAdapter.Companion.ItemDiffCallback
import com.example.bookie.network.model.Item
import com.example.bookie.preferences.adapter.PreferencesAdapter.MyHolder

class BooksAdapter(val data :List<Item>,val context:Context):
    PagingDataAdapter<Item, BooksAdapter.MyHolder>(ItemDiffCallback) {
    class MyHolder(row: View): RecyclerView.ViewHolder(row){
        val bookIv = row.findViewById<ImageView>(R.id.iv_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.book_item,parent,false)
        return MyHolder(row)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Glide.with(context).load(data[position].volumeInfo.imageLinks.thumbnail).into(holder.bookIv)
    }
}