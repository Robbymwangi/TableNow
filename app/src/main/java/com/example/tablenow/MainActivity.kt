package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.adapter.InfoCardAdapter
import com.example.tablenow.databinding.ActivityMainBinding
import com.example.tablenow.model.InfoCard
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Adapters
    private lateinit var featuredAdapter: InfoCardAdapter
    private lateinit var newAdapter: InfoCardAdapter

    // Data Lists
    private val featuredList = mutableListOf<InfoCard>()
    private val newList = mutableListOf<InfoCard>()

    // 1. Initialize Firestore
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbarAnimation()
        setupBottomNavigation()
        setupCategoryList()

        // 2. Setup Adapters (Empty at first)
        setupAdapters()

        // 3. Fetch REAL Data from Firestore
        fetchRestaurants()
    }

    private fun fetchRestaurants() {
        // Connect to the "restaurants" collection
        db.collection("restaurants")
            .get()
            .addOnSuccessListener { result ->
                // Clear lists so we don't duplicate data if this runs twice
                featuredList.clear()
                newList.clear()

                for (document in result) {
                    // Safely get data from Firestore fields
                    // The strings inside "" must match your Firestore fields EXACTLY
                    val name = document.getString("name") ?: "Unknown"
                    val category = document.getString("category") ?: "General"
                    val rating = document.getString("rating") ?: "0.0"
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val isFeatured = document.getBoolean("isFeatured") ?: false

                    // Create the InfoCard object
                    val restaurant = InfoCard(name, category, rating, imageUrl, isFeatured)

                    // Add to the "New" list (Everything goes here)
                    newList.add(restaurant)

                    // Add to "Featured" list ONLY if isFeatured is true
                    if (isFeatured) {
                        featuredList.add(restaurant)
                    }
                }

                // Tell the lists to refresh
                featuredAdapter.notifyDataSetChanged()
                newAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents.", exception)
                Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupAdapters() {
        // Setup Featured RecyclerView
        featuredAdapter = InfoCardAdapter(featuredList) { infoCard, _, _ -> openDetail(infoCard) }
        binding.rvFeatured.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = featuredAdapter

        // Setup New RecyclerView
        newAdapter = InfoCardAdapter(newList) { infoCard, _, _ -> openDetail(infoCard) }
        binding.rvNew.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNew.adapter = newAdapter
    }

    private fun openDetail(infoCard: InfoCard) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("RESTAURANT_NAME", infoCard.name)
        intent.putExtra("RESTAURANT_IMAGE", infoCard.imageUrl)
        intent.putExtra("RESTAURANT_RATING", infoCard.rating)
        startActivity(intent)
    }

    private fun setupToolbarAnimation() {
        val headerContainer = binding.root.findViewById<View>(R.id.headerExpandedContainer)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / totalScrollRange.toFloat()
            headerContainer.alpha = 1f - percentage
            if (percentage > 0.6f) {
                val fadeInAlpha = (percentage - 0.6f) * 2.5f
                binding.tvToolbarTitle.alpha = fadeInAlpha
            } else {
                binding.tvToolbarTitle.alpha = 0f
            }
        })
    }

    private fun setupCategoryList() {
        val categories = listOf(
            Category("Pizza", android.R.drawable.ic_menu_compass),
            Category("Burger", android.R.drawable.ic_menu_agenda),
            Category("Vegan", android.R.drawable.ic_menu_gallery),
            Category("Greek", android.R.drawable.ic_menu_directions),
            Category("Sushi", android.R.drawable.ic_menu_view)
        )
        val adapter = CategoryAdapter(categories)
        binding.rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = adapter
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> { Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show(); true }
                R.id.nav_profile -> {
                    startActivity(Intent(this, MybookingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}

// --- FIXED CATEGORY ADAPTER (No abstract errors) ---

data class Category(val name: String, val iconRes: Int)

class CategoryAdapter(private val list: List<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.ivCategoryIcon)
        val text: TextView = view.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = list[position]
        holder.text.text = item.name
        holder.icon.setImageResource(item.iconRes)
    }

    override fun getItemCount() = list.size
}