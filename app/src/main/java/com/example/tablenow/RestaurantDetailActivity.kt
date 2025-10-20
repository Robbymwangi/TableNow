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

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        // Get data from intent
        val imageUrl = intent.getStringExtra("image_url")
        val transitionName = intent.getStringExtra("transition_name")

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
    }
}