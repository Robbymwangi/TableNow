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

        // 1. Get Data from Previous Screen
        val name = intent.getStringExtra("RESTAURANT_NAME")
        val imageUrl = intent.getStringExtra("RESTAURANT_IMAGE")
        val rating = intent.getStringExtra("RESTAURANT_RATING")

        // 2. Setup UI Elements
        binding.restaurantName.text = name
        binding.restaurantAddress.text = "Main Street 6A" // Placeholder address
        binding.restaurantHours.text = "12:00-22:00"     // Placeholder hours

        // 3. Load Main Hero Image
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(binding.heroImage)

        // 4. Auto-Generate Brunch Image
        // This URL asks the internet for a random picture matching "brunch" or "breakfast"
        Glide.with(this)
            .load("https://loremflickr.com/600/400/brunch,breakfast")
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.brunchImage)

        // 5. Auto-Generate Dinner Image
        // This URL asks for a picture matching "steak" or "dinner"
        Glide.with(this)
            .load("https://loremflickr.com/600/400/steak,dinner")
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.dinnerImage)

        // 6. Back Button Logic
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        // 7. Booking Button Logic
        binding.bookTableButton.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", name)
            startActivity(intent)
        }
    }
}