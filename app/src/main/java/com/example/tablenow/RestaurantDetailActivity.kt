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
        binding.restaurantAddress.text = "Main Street 6A"
        binding.restaurantHours.text = "12:00-22:00"

        // 3. Load Main Hero Image
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(binding.heroImage)

        // 4. Auto-Generate Menu Images
        Glide.with(this).load("https://loremflickr.com/600/400/brunch,breakfast")
            .centerCrop().into(binding.brunchImage)
        Glide.with(this).load("https://loremflickr.com/600/400/steak,dinner")
            .centerCrop().into(binding.dinnerImage)

        binding.backButton.setOnClickListener { onBackPressed() }

        // 5. Booking Button Logic
        binding.bookTableButton.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("RESTAURANT_NAME", name)
            // IMPORTANT: Pass the image URL so the booking screen has it
            intent.putExtra("IMAGE_URL", imageUrl)
            startActivity(intent)
        }
    }
}