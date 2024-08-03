package com.example.bookie.search.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.R
import com.example.bookie.auth.signup.repo.SignUpRepoImp
import com.example.bookie.auth.signup.viewModel.SignUpViewModel
import com.example.bookie.auth.signup.viewModel.SignUpViewModelFactory
import com.example.bookie.databinding.FragmentSearchBinding
import com.example.bookie.network.ApiClient
import com.example.bookie.search.repo.SearchRepoImp
import com.example.bookie.search.viewModel.SearchViewModel
import com.example.bookie.search.viewModel.SearchViewModelFactory

class SearchFragment : Fragment() {

    private var TAG = "SearchFragment" 
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: SearchResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()
        adapter = SearchResultsAdapter()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // this function is called before text is edited
                if(s.toString().isNullOrEmpty()){
                    adapter.clearData()
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // this function is called when text is edited
                if(!s.toString().isNullOrEmpty()){
                    searchViewModel.searchForBooks(s.toString())
                }else{
                    adapter.clearData()
                }


            }

            override fun afterTextChanged(s: Editable) {
                // this function is called after text is edited
            }
        }

        binding.txtEtSearchBar.editText?.addTextChangedListener(textWatcher)

        searchViewModel.booksResults.observe(requireActivity()){results->
            if (results != null){
                binding.tvNoResults.visibility = View.GONE
                adapter.clearData()
                binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(requireContext())
//                val filteredBooks = results.items.filter { book ->
//                    !book.volumeInfo.title.isNullOrEmpty() || !book.volumeInfo.authors.isNullOrEmpty()
//                }
                // to remove duplicated books
                val uniqueTitles = mutableSetOf<String>()
                val filteredBooks = results.items.filter { book ->
                    val title = book.volumeInfo.title
                    if (!title.isNullOrEmpty() && !uniqueTitles.contains(title)) {
                        uniqueTitles.add(title)
                        true
                    } else {
                        false
                    }
                }

                adapter.setData(filteredBooks)
                binding.recyclerViewSearchResults.adapter = adapter

                Log.d(TAG, "onViewCreated: the results are ${results.items}")
            }else{
                binding.tvNoResults.visibility = View.VISIBLE
                Log.d(TAG, "onViewCreated: can't find any books results")
            }
        }

    }

    private fun getViewModelReady(){
        val factory = SearchViewModelFactory(SearchRepoImp(ApiClient))
        searchViewModel = ViewModelProvider(requireActivity(), factory)[SearchViewModel::class.java]
    }


}