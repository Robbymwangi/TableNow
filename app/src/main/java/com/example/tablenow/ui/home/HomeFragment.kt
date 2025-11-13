package com.example.tablenow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.Restaurant // Assumes Restaurant.kt exists
import com.example.tablenow.RestaurantAdapter // Assumes RestaurantAdapter.kt exists
import com.example.tablenow.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Adapters for each RecyclerView
    private lateinit var featuredAdapter: RestaurantAdapter
    private lateinit var newOnTableAdapter: RestaurantAdapter
    // private lateinit var categoryAdapter: CategoryAdapter // For future use

    // Separate lists for each section
    private val featuredList = mutableListOf<Restaurant>()
    private val newList = mutableListOf<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 1. Setup the toolbar animation
        setupToolbarAnimation()

        // 2. Setup the "Featured" RecyclerView
        setupFeaturedRecyclerView()

        // 3. Setup the "New on TableNow" RecyclerView
        setupNewOnRecyclerView()

        // 4. (Future) Setup the "Categories" RecyclerView
        // setupCategoriesRecyclerView()

        // 5. Load placeholder data into the lists
        loadPlaceholderData()

        return root
    }

    // --- NEW FUNCTION TO CONTROL THE COLLAPSING TOOLBAR ---
    private fun setupToolbarAnimation() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            // The percentage of collapse, from 0.0 (fully expanded) to 1.0 (fully collapsed)
            val scrollRange = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / scrollRange.toFloat()

            // Fade out the expanded title and search bar
            binding.headerContainer.alpha = 1.0f - (percentage * 1.5f)

            // Fade in the collapsed logo
            if (percentage > 0.8) { // Start fading in when it's mostly collapsed
                binding.tableNowLogoCollapsed.visibility = View.VISIBLE
                // Calculate alpha to fade in smoothly from 80% to 100% collapse
                binding.tableNowLogoCollapsed.alpha = (percentage - 0.8f) / 0.2f
            } else {
                binding.tableNowLogoCollapsed.visibility = View.GONE
                binding.tableNowLogoCollapsed.alpha = 0f
            }
        })
    }
    // --- END OF NEW FUNCTION ---

    private fun setupFeaturedRecyclerView() {
        featuredAdapter = RestaurantAdapter(featuredList)
        binding.featuredRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.featuredRecyclerView.adapter = featuredAdapter
    }

    private fun setupNewOnRecyclerView() {
        newOnTableAdapter = RestaurantAdapter(newList)
        binding.newOnBookableRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.newOnBookableRecyclerView.adapter = newOnTableAdapter
    }

    private fun loadPlaceholderData() {
        featuredList.clear()
        newList.clear()

        val restaurant1 = Restaurant("res123", "The Gourmet Place", "Italian", "4.8", "https://picsum.photos/id/20/400/300")
        val restaurant2 = Restaurant("res456", "Sizzling Grillhouse", "Steakhouse", "4.5", "https://picsum.photos/id/30/400/300")
        val restaurant3 = Restaurant("res789", "Sushi Central", "Japanese", "4.9", "https://picsum.photos/id/40/400/300")
        val restaurant4 = Restaurant("res101", "Pasta Paradise", "Italian", "4.6", "https://picsum.photos/id/50/400/300")


        featuredList.addAll(listOf(restaurant1, restaurant3, restaurant4))
        newList.addAll(listOf(restaurant2, restaurant1))

        featuredAdapter.notifyDataSetChanged()
        newOnTableAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Important: Remove the listener to prevent memory leaks
        binding.appBarLayout.removeOnOffsetChangedListener(null)
        _binding = null
    }
}
