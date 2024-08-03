package com.example.bookie.foryou.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.R
import com.example.bookie.foryou.repo.ForYouRepoImp
import com.example.bookie.foryou.viewmodel.ForYouViewModel
import com.example.bookie.foryou.viewmodel.ForYouViewModelFactory
import com.example.bookie.network.ApiClient

class ForUFragment : Fragment() {
    private lateinit var viewModel: ForYouViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_for_u, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        viewModel.getUserPreferences()
        viewModel.userSelectedItems.observe(requireActivity()){
         //   Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        }
        viewModel.getBooks("books")
        viewModel.book.observe(requireActivity()){
            Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun gettingViewModelReady(){
        val forYouViewModelFactory = ForYouViewModelFactory(ForYouRepoImp(ApiClient))
        viewModel=ViewModelProvider(requireActivity(),forYouViewModelFactory).get(ForYouViewModel::class)
    }
}