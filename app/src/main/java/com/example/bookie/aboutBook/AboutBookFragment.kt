package com.example.bookie.aboutBook


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.databinding.FragmentAboutBookBinding


class AboutBookFragment : Fragment() {
    private lateinit var _binding :FragmentAboutBookBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentAboutBookBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: AboutBookFragmentArgs by navArgs()
        super.onViewCreated(view, savedInstanceState)
        _binding.tvBookTitle.text=args.aboutBook.bookTitle
        _binding.tvBookAuthor.text=args.aboutBook.bookAuthor
        _binding.tvBookDescription.text=args.aboutBook.bookDescription
        Glide.with(requireContext())
            .load(args.aboutBook.bookImage)
            .placeholder(R.drawable.baseline_error_outline_24)
            .into(_binding.ivBook)
        Glide.with(requireContext())
            .load(args.aboutBook.bookImage)
            .placeholder(R.drawable.baseline_error_outline_24)
            .into(_binding.ivBookLayout)
    }

}