package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityRestaurantDetailBinding
import coil.load

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Set click listener for the custom back button ImageButton
        binding.backButton.setOnClickListener { 
            supportFinishAfterTransition() // Ensures shared element transition works on back press
        }

        // Get data from intent
        val imageUrl = intent.getStringExtra("image_url")
        val transitionName = intent.getStringExtra("transition_name")
        val restaurantName = intent.getStringExtra("restaurant_name") // Retrieve restaurant name

        // Set up shared element transition
        supportPostponeEnterTransition()
        if (transitionName != null) {
            binding.heroImage.transitionName = transitionName
        }

        // Load image
        if (imageUrl != null) {
            binding.heroImage.load(imageUrl) {
                crossfade(true)
                listener {
                    request, result -> supportStartPostponedEnterTransition() 
                }
                error(R.drawable.placeholder_restaurant_image)
            }
        } else {
            binding.heroImage.setImageResource(R.drawable.placeholder_restaurant_image)
            supportStartPostponedEnterTransition()
        }
        
        // Set the restaurant name if available
        if (restaurantName != null) {
            binding.restaurantName.text = restaurantName
        }

        binding.brunchImage.load("https://images.unsplash.com/photo-1525351484163-7529414344d8?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"){
            crossfade(true)
            error(R.drawable.placeholder_menu_image)
        }
        binding.dinnerImage.load("https://images.unsplash.com/photo-1555939594-58d7cb561ad1?q=80&w=2787&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"){
            crossfade(true)
            error(R.drawable.placeholder_menu_image)
        }
    }
}