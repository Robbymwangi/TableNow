package com.example.tablenow.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.R
import com.example.tablenow.adapter.CategoryAdapter
import com.example.tablenow.adapter.InfoCardAdapter
import com.example.tablenow.adapter.SkeletonAdapter
import com.example.tablenow.databinding.FragmentHomeBinding
import com.example.tablenow.model.Category
import com.example.tablenow.model.InfoCard

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up all the RecyclerViews (Categories, Featured, etc.)
        setupRecyclerViews()

        // Set up the Bottom Navigation, now that the side drawer is gone.
        setupBottomNavigation()
    }

    private fun setupRecyclerViews() {
        // 1. Categories RecyclerView
        val categories = listOf(
            Category("Pizza", R.drawable.ic_pizza_slice),
            Category("Burger", R.drawable.ic_burger),
            Category("Vegan", R.drawable.ic_leaf),
            Category("Greek", R.drawable.ic_greek_columns)
        )
        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = CategoryAdapter(categories)
        }

        // 2. Set up initial skeleton loaders for a better loading experience
        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = SkeletonAdapter(5)
        }
        binding.newOnBookableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = SkeletonAdapter(5)
        }

        // 3. Simulate a network call and load the real data
        Handler(Looper.getMainLooper()).postDelayed({
            // Click listener for navigating to the detail screen with a shared element transition
            val onCardClick = { card: InfoCard, imageView: ImageView ->
                val extras = FragmentNavigatorExtras(imageView to imageView.transitionName)
                findNavController().navigate(
                    R.id.action_nav_home_to_restaurantDetailFragment, // Ensure this action is in your nav_graph.xml
                    null,
                    null,
                    extras
                )
            }

            // Populate "Featured" section
            val featuredCards = listOf(
                InfoCard("The Talisman Restaurant", "Fine Dining · Karen", "4.8", "https://picsum.photos/400/300?random=1", true),
                InfoCard("About Thyme", "Continental · Westlands", "4.7", "https://picsum.photos/400/300?random=2", true),
                InfoCard("Tamarind Tree Hotel", "Modern African · Lang'ata", "4.9", "https://picsum.photos/400/300?random=3", true),
                InfoCard("The Carnivore", "Nyama Choma · Lang'ata", "4.5", "https://picsum.photos/400/300?random=4", true),
                InfoCard("Fogo Gaucho", "Brazilian Steakhouse · Kilimani", "4.6", "https://picsum.photos/400/300?random=5", true)
            )
            binding.featuredRecyclerView.adapter = InfoCardAdapter(featuredCards, onCardClick)

            // Populate "New on Bookable" section
            val newOnBookableCards = listOf(
                InfoCard("CJ's Restaurant", "Cafe · Koinange St", "4.6", "https://picsum.photos/400/300?random=6"),
                InfoCard("Artcaffe", "Coffee & Bakery · The Hub", "4.5", "https://picsum.photos/400/300?random=7"),
                InfoCard("Java House", "Coffee House · Mama Ngina St", "4.4", "https://picsum.photos/400/300?random=8"),
                InfoCard("Big Square", "Fast Food · Westlands", "4.2", "https://picsum.photos/400/300?random=9"),
                InfoCard("Mama Oliech's", "Local Cuisine · Hurlingham", "4.7", "https://picsum.photos/400/300?random=10")
            )
            binding.newOnBookableRecyclerView.adapter = InfoCardAdapter(newOnBookableCards, onCardClick)
        }, 2000) // 2-second delay to simulate loading
    }

    private fun setupBottomNavigation() {
        val navController = findNavController()
        // We handle item selection manually to allow for custom logic,
        // especially for the Profile/Avatar tab which isn't a standard icon.
        binding.bottomNavView.setOnItemSelectedListener { item ->
            // You can add custom logic here, like adding the ring to the avatar when the profile tab is selected.
            // For now, we'll use the standard NavigationUI behavior.
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already here, do nothing or refresh
                    true
                }
                R.id.nav_search, R.id.nav_bookings, R.id.nav_profile -> {
                    // Navigate to the other destinations
                    navController.navigate(item.itemId)
                    true
                }
                else -> false
            }
        }
    }

    // This function is removed as it would cause a crash.
    // The TextView 'toolbarLogoText' was deleted from the XML layout.
    // private fun setupCollapsingToolbar() { ... }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}