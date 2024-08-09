package com.example.bookie.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.FrameMetricsAggregator
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.bookie.R
import com.example.bookie.databinding.FragmentHomeBinding
import com.example.bookie.home.repo.HomeRepoImp
import com.example.bookie.home.viewmodel.HomeViewModel
import com.example.bookie.home.viewmodel.HomeViewModelFactory
import com.example.bookie.network.ApiClient
import com.example.bookie.search.repo.SearchRepoImp
import com.example.bookie.search.viewModel.SearchViewModel
import com.example.bookie.search.viewModel.SearchViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlin.math.log


class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeBooksAdapter: HomeBooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()
        homeBooksAdapter = HomeBooksAdapter(requireContext())
        binding.rvBooksHome.layoutManager= LinearLayoutManager(requireContext())
        binding.rvBooksHome.adapter = homeBooksAdapter

        binding.bottomNavigation.setOnItemSelectedListener { item ->

                when(item.itemId) {
                    R.id.search -> {
                        findNavController().navigate(R.id.searchFragment)
                        true
                    }
                    R.id.home -> {
                        view.findNavController().navigate(R.id.homeFragment)
                        true
                    }
                    R.id.for_you -> {
                        view.findNavController().navigate(R.id.forUFragment)
                        true
                    }
                    R.id.profile -> {
//                        view.findNavController().navigate(R.id.profileFragment)
                        true
                    }
                    else -> false

                }
        }

        homeViewModel.getPagedBooks()

        homeViewModel.pagedBooks.observe(requireActivity()){books->
            homeBooksAdapter.submitData(lifecycle , books)
            homeBooksAdapter.notifyDataSetChanged()

        }


    }

    private fun getViewModelReady(){
        val factory = HomeViewModelFactory(HomeRepoImp(ApiClient))
        homeViewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
    }

}