package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tablenow.databinding.ActivityRestaurantDetailBinding

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("RESTAURANT_NAME")
        val imageUrl = intent.getStringExtra("RESTAURANT_IMAGE")
        val rating = intent.getStringExtra("RESTAURANT_RATING")

        binding.restaurantName.text = name // Changed from detailTitle
        // Use Glide to load this imageUrl into the detail screen's ImageView
        Glide.with(this)
            .load(imageUrl)
            .into(binding.heroImage) // Changed to heroImage

        binding.bookTableButton.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", name) // Pass data to booking
            startActivity(intent)
        }
    }
}
