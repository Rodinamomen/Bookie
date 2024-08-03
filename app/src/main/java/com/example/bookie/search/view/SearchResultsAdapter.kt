package com.example.bookie.search.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.network.model.BookResponse
import com.example.bookie.network.model.Item

class SearchResultsAdapter(): RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder>() {

    private val TAG = "SearchResultsAdapter"
    private var resultsData = listOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_results,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultsData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bookTitle.text = resultsData[position].volumeInfo.title
        if (formatAuthors(resultsData[position].volumeInfo.authors).isNullOrEmpty()){
            holder.bookAuthor.visibility = View.GONE
        }else{
            holder.bookAuthor.visibility = View.VISIBLE
            holder.bookAuthor.text = formatAuthors(resultsData[position].volumeInfo.authors)
        }

        holder.itemView.setOnClickListener {
            // navigate to the book details fragment
            Log.d(TAG, "onBindViewHolder: book number ${position} is clicked.")
        }
    }

    fun setData(data: List<Item>?){
        if (!data.isNullOrEmpty()){
            resultsData = data
        }
        notifyDataSetChanged()
    }

    fun clearData(){
        resultsData = listOf()
        notifyDataSetChanged()
    }
    fun formatAuthors(authors: List<String>?): String {
        return when {
            authors.isNullOrEmpty() -> ""
            authors.size == 1 -> "by ${authors[0]}"
            authors.size == 2 -> "by ${authors[0]} and ${authors[1]}"
            else -> "by " + authors.dropLast(1).joinToString(", ") + ", and " + authors.last()
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView
        val bookAuthor: TextView

        init {
            bookTitle = view.findViewById(R.id.tv_book_title_results)
            bookAuthor = view.findViewById(R.id.tv_aurthor_results)
        }
    }
}

