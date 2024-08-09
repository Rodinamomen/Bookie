package com.example.bookie.foryou.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R

import com.example.bookie.network.model.Item
object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
class ForYouAdapter(val subject:String, val context: Context):PagingDataAdapter<Item, ForYouAdapter.MyHolder>(ItemDiffCallback) {
    class MyHolder(val row: View): RecyclerView.ViewHolder(row){
        val bookSubject = row.findViewById<TextView>(R.id.tv_book_subject)
   //     val booksRv = row.findViewById<RecyclerView>(R.id.rv_books_images)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.books_parent_item,parent,false)
        return MyHolder(row)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bookSubject.text="subject"
      /*  val booksAdapter= TestAdapter(context)
       holder.booksRv.adapter=booksAdapter*/
    }
}