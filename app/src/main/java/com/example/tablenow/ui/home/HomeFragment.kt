package com.example.tablenow.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.RestaurantDetailActivity
import com.example.tablenow.adapter.InfoCardAdapter
import com.example.tablenow.databinding.FragmentHomeBinding
import com.example.tablenow.model.InfoCard
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // --- THESE WERE MISSING ---
    // These define the variables so the functions below can use them
    private lateinit var featuredAdapter: InfoCardAdapter
    private lateinit var newOnTableAdapter: InfoCardAdapter
    private val featuredList = mutableListOf<InfoCard>()
    private val newList = mutableListOf<InfoCard>()
    // --------------------------

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 1. Setup the Toolbar Collapsing Animation
        setupToolbarAnimation()

        // 2. Setup the RecyclerViews
        setupFeaturedRecyclerView()
        setupNewOnRecyclerView()

        // 3. Load the Data
        loadPlaceholderData()

        return root
    }

    private fun setupToolbarAnimation() {
        // This handles the fading of the logo when you scroll up
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / scrollRange.toFloat()

            // Fade out the big header content
            binding.headerContainer.alpha = 1.0f - (percentage * 1.5f)

            // Show the small logo in the toolbar when collapsed
            if (percentage > 0.8) {
                binding.tableNowLogoCollapsed.visibility = View.VISIBLE
                binding.tableNowLogoCollapsed.alpha = (percentage - 0.8f) / 0.2f
            } else {
                binding.tableNowLogoCollapsed.visibility = View.GONE
                binding.tableNowLogoCollapsed.alpha = 0f
            }
        })
    }

    private fun setupFeaturedRecyclerView() {
        featuredAdapter = InfoCardAdapter(featuredList) { infoCard, _, _ ->
            val intent = Intent(requireActivity(), RestaurantDetailActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", infoCard.name)
            intent.putExtra("RESTAURANT_IMAGE", infoCard.imageUrl)
            intent.putExtra("RESTAURANT_RATING", infoCard.rating)
            startActivity(intent)
        }
        binding.featuredRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.featuredRecyclerView.adapter = featuredAdapter
    }

    private fun setupNewOnRecyclerView() {
        newOnTableAdapter = InfoCardAdapter(newList) { infoCard, _, _ ->
            val intent = Intent(requireActivity(), RestaurantDetailActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", infoCard.name)
            intent.putExtra("RESTAURANT_IMAGE", infoCard.imageUrl)
            intent.putExtra("RESTAURANT_RATING", infoCard.rating)
            startActivity(intent)
        }
        binding.newOnBookableRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.newOnBookableRecyclerView.adapter = newOnTableAdapter
    }

    private fun loadPlaceholderData() {
        featuredList.clear()
        newList.clear()

        // We added 'isFeatured' to ensure the data model matches your InfoCard class
        val infoCard1 = InfoCard("The Gourmet Place", "Italian", "4.8", "https://picsum.photos/id/20/400/300", isFeatured = true)
        val infoCard2 = InfoCard("Sizzling Grillhouse", "Steakhouse", "4.5", "https://picsum.photos/id/30/400/300", isFeatured = false)
        val infoCard3 = InfoCard("Sushi Central", "Japanese", "4.9", "https://picsum.photos/id/40/400/300", isFeatured = true)
        val infoCard4 = InfoCard("Pasta Paradise", "Italian", "4.6", "https://picsum.photos/id/50/400/300", isFeatured = true)

        featuredList.addAll(listOf(infoCard1, infoCard3, infoCard4))
        newList.addAll(listOf(infoCard2, infoCard1))

        featuredAdapter.notifyDataSetChanged()
        newOnTableAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}