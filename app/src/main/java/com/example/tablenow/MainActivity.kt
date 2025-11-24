package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.adapter.InfoCardAdapter
import com.example.tablenow.databinding.ActivityMainBinding
import com.example.tablenow.model.InfoCard

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Standard View Binding Setup
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Bottom Navigation Click Listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // 2. Prepare Data
        val data = getMockData()

        // 3. Setup Featured List
        val featuredAdapter = InfoCardAdapter(data) { infoCard, _, _ ->
            // Handle Click
            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", infoCard.name)
            intent.putExtra("RESTAURANT_IMAGE", infoCard.imageUrl)
            startActivity(intent)
        }
        binding.rvFeatured.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeatured.adapter = featuredAdapter

        // 4. Setup New List
        val newAdapter = InfoCardAdapter(data) { infoCard, _, _ ->
            // Handle Click
            val intent = Intent(this, RestaurantDetailActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", infoCard.name)
            intent.putExtra("RESTAURANT_IMAGE", infoCard.imageUrl)
            startActivity(intent)
        }
        binding.rvNew.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNew.adapter = newAdapter
    }

    private fun getMockData(): List<InfoCard> {
        return listOf(
            InfoCard(
                "The Gourmet Place",
                "Italian",
                "4.8",
                "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=500&auto=format&fit=crop&q=60",
                true
            ),
            InfoCard(
                "Sushi Central",
                "Japanese",
                "4.9",
                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=500&auto=format&fit=crop&q=60",
                true
            ),
             InfoCard(
                "Burger King",
                "American",
                "4.2",
                "https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=500&auto=format&fit=crop&q=60",
                true
            )
        )
    }
}
