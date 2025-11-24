package com.example.tablenow

import android.content.Intent
import android.os.Bundle
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
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbarAnimation()
        setupBottomNavigation()
        
        val data = getMockData()
        setupFeaturedList(data)
        setupNewList(data)
        setupCategoryList()
    }

    // --- 1. THE ANIMATION LOGIC ---
    private fun setupToolbarAnimation() {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / totalScrollRange.toFloat()

            // 0.0 = Expanded (See "Discover")
            // 1.0 = Collapsed (See "TableNow")

            // Fade OUT the Big Header
            binding.tvHeaderExpanded.alpha = 1f - percentage
            
            // Fade IN the Toolbar Title (Starts appearing after 50% scroll)
            if (percentage > 0.5f) {
                // Map 0.5->1.0 to 0.0->1.0 for opacity
                val fadeInAlpha = (percentage - 0.5f) * 2
                binding.tvToolbarTitle.alpha = fadeInAlpha
            } else {
                binding.tvToolbarTitle.alpha = 0f
            }
        })
    }

    private fun setupCategoryList() {
        val categories = listOf(
            Category("Pizza", R.drawable.baseline_fastfood_24),
            Category("Burger", R.drawable.baseline_local_pizza_24),
            Category("Vegan", android.R.drawable.ic_menu_agenda),
            Category("Greek", android.R.drawable.ic_menu_camera),
            Category("Sushi", android.R.drawable.ic_menu_share)
        )
        
        val adapter = CategoryAdapter(categories)
        binding.rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = adapter
    }

    private fun setupFeaturedList(data: List<InfoCard>) {
        val adapter = InfoCardAdapter(data) { infoCard, _, _ -> openDetail(infoCard) }
        binding.rvFeatured.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = adapter
    }

    private fun setupNewList(data: List<InfoCard>) {
        val adapter = InfoCardAdapter(data) { infoCard, _, _ -> openDetail(infoCard) }
        binding.rvNew.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNew.adapter = adapter
    }

    private fun openDetail(infoCard: InfoCard) {
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra("RESTAURANT_NAME", infoCard.name)
        intent.putExtra("RESTAURANT_IMAGE", infoCard.imageUrl)
        startActivity(intent)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> { Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show(); true }
                R.id.nav_profile -> { Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show(); true }
                else -> false
            }
        }
    }

    private fun getMockData(): List<InfoCard> {
        return listOf(
            InfoCard("The Gourmet Place", "Italian", "4.8", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=500&auto=format&fit=crop&q=60", true),
            InfoCard("Sushi Central", "Japanese", "4.9", "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=500&auto=format&fit=crop&q=60", true),
            InfoCard("Burger King", "American", "4.2", "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=500&auto=format&fit=crop&q=60", true)
        )
    }
}

// --- SMALL DATA CLASS & ADAPTER FOR CATEGORIES ---

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