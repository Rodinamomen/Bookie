package com.example.bookie.foryou.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.databinding.FragmentForUBinding
import com.example.bookie.databinding.FragmentPreferencesBinding
import com.example.bookie.foryou.adapters.ForYouAdapter
import com.example.bookie.foryou.adapters.MainAdapter
import com.example.bookie.foryou.adapters.TestAdapter
import com.example.bookie.foryou.model.AboutBook
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
  // private lateinit var mainAdapter: MainAdapter
   private lateinit var adapter: TestAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentForUBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()
        viewModel.getUserPreferences()
        viewModel.userSelectedItems.observe(viewLifecycleOwner) { items ->
            /*  if (items != null && items.isNotEmpty()) {
                     val mainAdapter = MainAdapter(items, viewModel, viewLifecycleOwner, requireContext(), lifecycle)
                     binding.rvForYou.adapter = mainAdapter
                     binding.rvForYou.layoutManager = LinearLayoutManager(requireContext())
                 } else {
                     Log.e("ForUFragment", "userSelectedItems is null or empty")
                 }*/
            for(item in items){
                viewModel.getBooks(makeQuery(item))
            }


         /*   if (items != null && items.isNotEmpty()) {
                val mainAdapter = MainAdapter(items, viewModel, viewLifecycleOwner, requireContext(), lifecycle)
                binding.rvForYou.adapter = mainAdapter
                binding.rvForYou.layoutManager = LinearLayoutManager(requireContext())
            } else {
                Log.e("ForUFragment", "userSelectedItems is null or empty")
            }*/
        }
     /*   binding.rvForYou.adapter =mainAdapter
        binding.rvForYou.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)*/
      viewModel.books.observe(viewLifecycleOwner) {  pagingData->
            Toast.makeText(requireContext(), "$pagingData", Toast.LENGTH_SHORT).show()
            adapter= TestAdapter(requireContext())
            binding.rvForYou.adapter =adapter
            binding.rvForYou.layoutManager=  GridLayoutManager(requireActivity(),2)
            adapter.submitData(lifecycle, pagingData)
            adapter.setOnClickListener(object :TestAdapter.OnItemClickListener{
                override fun onItemClicked(bookItem: AboutBook) {
                    val action = ForUFragmentDirections.actionForUFragmentToAboutBookFragment(bookItem)
                    findNavController().navigate(action)
                }
            })
        }
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
    }
    private fun makeQuery(listOfPreferences: String): String {
        val query =  "subject:${listOfPreferences}"
        Log.d("testing", "makeQuery:$query ")
       return query
    }
    private fun gettingViewModelReady(){
        val forYouViewModelFactory = ForYouViewModelFactory(ForYouRepoImp(ApiClient))
        viewModel=ViewModelProvider(requireActivity(),forYouViewModelFactory).get(ForYouViewModel::class)
    }
}