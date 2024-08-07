package com.example.bookie.foryou.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.network.model.Item

class TestAdapter(val context: Context): PagingDataAdapter<Item, TestAdapter.MyHolder>(ItemDiffCallback) {

    class MyHolder(val row: View): RecyclerView.ViewHolder(row) {
        val iv = row.findViewById<ImageView>(R.id.iv_book)
    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            val thumbnailUrl = item.volumeInfo?.imageLinks?.thumbnail
            Log.d("image", "onBindViewHolder:$thumbnailUrl ")
                Glide.with(context)
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.baseline_error_outline_24)
                    .into(holder.iv)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return MyHolder(row)
    }

    companion object {
        val ItemDiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}
