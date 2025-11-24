package com.example.tablenow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.model.InfoCard // Changed import to InfoCard
import com.example.tablenow.adapter.InfoCardAdapter // Changed import to InfoCardAdapter
import com.example.tablenow.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Adapters for each RecyclerView
    private lateinit var featuredAdapter: InfoCardAdapter // Changed type to InfoCardAdapter
    private lateinit var newOnTableAdapter: InfoCardAdapter // Changed type to InfoCardAdapter
    // private lateinit var categoryAdapter: CategoryAdapter // For future use

    // Separate lists for each section
    private val featuredList = mutableListOf<InfoCard>() // Changed type to InfoCard
    private val newList = mutableListOf<InfoCard>() // Changed type to InfoCard

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
        featuredAdapter = InfoCardAdapter(featuredList, onClick = {_,_,_ ->}) // Changed to InfoCardAdapter and added dummy onClick
        binding.featuredRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.featuredRecyclerView.adapter = featuredAdapter
    }

    private fun setupNewOnRecyclerView() {
        newOnTableAdapter = InfoCardAdapter(newList, onClick = {_,_,_ ->}) // Changed to InfoCardAdapter and added dummy onClick
        binding.newOnBookableRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.newOnBookableRecyclerView.adapter = newOnTableAdapter
    }

    private fun loadPlaceholderData() {
        featuredList.clear()
        newList.clear()

        val infoCard1 = InfoCard("The Gourmet Place", "Italian", "4.8", "https://picsum.photos/id/20/400/300", isFeatured = true)
        val infoCard2 = InfoCard("Sizzling Grillhouse", "Steakhouse", "4.5", "https://picsum.photos/id/30/400/300")
        val infoCard3 = InfoCard("Sushi Central", "Japanese", "4.9", "https://picsum.photos/id/40/400/300", isFeatured = true)
        val infoCard4 = InfoCard("Pasta Paradise", "Italian", "4.6", "https://picsum.photos/id/50/400/300", isFeatured = true)


        featuredList.addAll(listOf(infoCard1, infoCard3, infoCard4))
        newList.addAll(listOf(infoCard2, infoCard1))

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
