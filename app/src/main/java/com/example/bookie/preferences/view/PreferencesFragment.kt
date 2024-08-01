package com.example.bookie.preferences.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookie.databinding.FragmentPreferencesBinding
import com.example.bookie.preferences.adapter.PreferencesAdapter


class PreferencesFragment : Fragment() {
    private lateinit var binding : FragmentPreferencesBinding
    val bisacMainCategories = listOf(
        "Antique & Collectibles",
        "Architecture",
        "Art",
        "Bibles",
        "Biography & Autobiography",
        "Body, Mind & Spirit",
        "Business & Economics",
        "Comics & Graphic Novels",
        "Computers",
        "Cooking",
        "Crafts & Hobbies",
        "Design",
        "Drama",
        "Education",
        "Family & Relationships",
        "Fiction",
        "Foreign Language Study",
        "Games & Activities",
        "Gardening",
        "Health & Fitness",
        "History",
        "House & Home",
        "Humor",
        "Juvenile Fiction",
        "Juvenile Nonfiction",
        "Language Arts & Disciplines",
        "Law",
        "Literary Collections",
        "Literary Criticism",
        "Mathematics",
        "Medical",
        "Music",
        "Nature",
        "Performing Arts",
        "Pets",
        "Philosophy",
        "Photography",
        "Poetry",
        "Political Science",
        "Psychology",
        "Reference",
        "Religion",
        "Science",
        "Self-Help",
        "Social Science",
        "Sports & Recreation",
        "Study Aids",
        "Technology & Engineering",
        "Transportation",
        "Travel",
        "True Crime",
        "Young Adult Fiction",
        "Young Adult Nonfiction"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PreferencesAdapter(bisacMainCategories,requireContext())
        binding.rvCategories.adapter=adapter
        binding.rvCategories.layoutManager= GridLayoutManager(requireActivity(),2)
        binding.btnNext.setOnClickListener {
            Toast.makeText(requireContext(), "${adapter.getSelectedItem()}", Toast.LENGTH_SHORT).show()
        }
    }

}