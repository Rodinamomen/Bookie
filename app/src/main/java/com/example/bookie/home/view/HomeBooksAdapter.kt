package com.example.bookie.home.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.network.model.Item

class HomeBooksAdapter(var context: Context): RecyclerView.Adapter<HomeBooksAdapter.MyViewHolder>() {
//class HomeBooksAdapter(var context: Context): PagingDataAdapter<Item, HomeBooksAdapter.MyViewHolder>(BOOK_COMPARATOR) {

    private val TAG = "HomeBooksAdapter"
    private var data = listOf<Item>()

//    companion object {
//        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
//            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_home, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: 1")
        holder.bookTitle.text = data[position].volumeInfo.title

        if (formatAuthors(data[position].volumeInfo.authors).isNullOrEmpty()){
            holder.bookAuthor.visibility = View.INVISIBLE
        }else{
            holder.bookAuthor.visibility = View.VISIBLE
            holder.bookAuthor.text = formatAuthors(data[position].volumeInfo.authors)
        }

        if (formatAuthors(data[position].volumeInfo.categories).isNullOrEmpty()){
            holder.bookCategory.visibility = View.INVISIBLE
        }else{
            holder.bookCategory.visibility = View.VISIBLE
            holder.bookCategory.text = formatCategories(data[position].volumeInfo.categories)
        }

        val securedImageLink = data[position].volumeInfo.imageLinks.thumbnail.replace("http", "https")

        Glide.with(context)
            .load(securedImageLink)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.bookImage)
    }

    fun setData(books: List<Item>){
//        if (!data.isNullOrEmpty()){
            data = books
//        }
        notifyDataSetChanged()
    }

    private fun formatAuthors(authors: List<String>?): String {
        return when {
            authors.isNullOrEmpty() -> ""
            authors.size == 1 -> "by ${authors[0]}"
            authors.size == 2 -> "by ${authors[0]} and ${authors[1]}"
            else -> "by " + authors.dropLast(1).joinToString(", ") + ", and " + authors.last()
        }
    }
    private fun formatCategories(categories: List<String>?): String {
        return when {
            categories.isNullOrEmpty() -> ""
            categories.size == 1 -> categories[0]
            categories.size == 2 -> "${categories[0]} and ${categories[1]}"
            else -> categories.dropLast(1).joinToString(", ") + ", and " + categories.last()
        }
    }

    class MyViewHolder(item: View): RecyclerView.ViewHolder(item){

        var bookTitle = item.findViewById<TextView>(R.id.tv_book_title_home)
        var bookAuthor = item.findViewById<TextView>(R.id.tv_book_author_home)
        var bookCategory = item.findViewById<TextView>(R.id.tv_book_category_home)
        var bookImage = item.findViewById<ImageView>(R.id.img_book_thumbnail_home)

    }
}