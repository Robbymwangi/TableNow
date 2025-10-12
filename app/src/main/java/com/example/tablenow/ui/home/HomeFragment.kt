package com.example.tablenow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerViews()

        return root
    }

    private fun setupRecyclerViews() {
        binding.nearYouRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RestaurantAdapter(listOf("Restaurant A", "Restaurant B", "Restaurant C", "Restaurant D", "Restaurant E"))
        }

        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(listOf("Pizza", "Burger", "Vegan", "Sushi", "Italian"))
        }

        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = FeaturedAdapter(listOf("Featured 1", "Featured 2", "Featured 3"))
        }

        binding.newOnBookableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RestaurantAdapter(listOf("New Place 1", "New Place 2", "New Place 3", "New Place 4", "New Place 5"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}