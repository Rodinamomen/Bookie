package com.example.bookie.foryou.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.foryou.adapters.ForYouAdapter.MyHolder
import com.example.bookie.foryou.adapters.TestAdapter.Companion.ItemDiffCallback
import com.example.bookie.foryou.model.AboutBook
import com.example.bookie.foryou.viewmodel.ForYouViewModel
import com.example.bookie.network.model.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainAdapter(
    private val subjects: List<String>,
    private val viewModel: ForYouViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val lifecycle: Lifecycle,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectTextView: TextView = view.findViewById(R.id.tv_book_subject)
        val childRecyclerView: RecyclerView = view.findViewById(R.id.rv_books_images)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.books_parent_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val subject = subjects[position]
        Log.d("NetworkRequest", "onBindViewHolder: $subject")
        holder.subjectTextView.text = subject
        viewModel.getBooks(subject)
        viewModel.books.observe(viewLifecycleOwner){ pagingData ->
            val childAdapter = TestAdapter(context)
            holder.childRecyclerView.adapter = childAdapter
            childAdapter.submitData(lifecycle, pagingData)
            Log.d("PagingData", "Data submitted to adapter: ${pagingData.toString()}")
            holder.childRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            childAdapter.submitData(lifecycle, pagingData)
            childAdapter.setOnClickListener(object :TestAdapter.OnItemClickListener {
                override fun onItemClicked(bookItem: AboutBook) {
                    /*     val action = ForUFragmentDirections.actionForUFragmentToAboutBookFragment(bookItem)
                    findNavController().navigate(action)*/
                }
            })
    }
    }

    override fun getItemCount(): Int {
        return subjects.size
    }
}
