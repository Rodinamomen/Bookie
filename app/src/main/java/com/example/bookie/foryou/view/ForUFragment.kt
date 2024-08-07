package com.example.bookie.foryou.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.databinding.FragmentForUBinding
import com.example.bookie.databinding.FragmentPreferencesBinding
import com.example.bookie.foryou.adapters.ForYouAdapter
import com.example.bookie.foryou.adapters.TestAdapter
import com.example.bookie.foryou.repo.ForYouRepoImp
import com.example.bookie.foryou.viewmodel.ForYouViewModel
import com.example.bookie.foryou.viewmodel.ForYouViewModelFactory
import com.example.bookie.network.ApiClient
import com.example.bookie.network.model.ImageLinks
import com.example.bookie.network.model.Item
import com.example.bookie.network.model.VolumeInfo
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ForUFragment : Fragment() {
    private lateinit var viewModel: ForYouViewModel
    private lateinit var binding: FragmentForUBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        binding= FragmentForUBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        viewModel.getUserPreferences()
        viewModel.userSelectedItems.observe(requireActivity()){

            viewModel.getBooks(makeQuery(it).trim())
        }
        viewModel.book.observe(viewLifecycleOwner) { pagingData ->
            Toast.makeText(requireContext(), "$pagingData", Toast.LENGTH_SHORT).show()
            val adapter = TestAdapter(requireContext())
            binding.rvTest.adapter = adapter
            binding.rvTest.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter.submitData(lifecycle, pagingData)
        }
    }

    private fun makeQuery(listOfPreferences: List<String>): String {
        val query = listOfPreferences
            .joinToString(separator = "+or+") { "subject:${it}" }
          /*  .replace(" ", "")
            .replace(",","")
            .replace("&","")*/

        Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
        Log.d("testing", "makeQuery:$query ")
       return query

    }
    private fun gettingViewModelReady(){
        val forYouViewModelFactory = ForYouViewModelFactory(ForYouRepoImp(ApiClient))
        viewModel=ViewModelProvider(requireActivity(),forYouViewModelFactory).get(ForYouViewModel::class)
    }
}