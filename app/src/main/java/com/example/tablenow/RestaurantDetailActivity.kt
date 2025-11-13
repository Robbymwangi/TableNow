package com.example.tablenow

import android.content.Intent // <-- FIX #1: Adds the missing import
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityRestaurantDetailBinding

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantId = intent.getStringExtra("RESTAURANT_ID")

        binding.bookTableButton.setOnClickListener {
            // This line will now work because BookingActivity exists
            // and the Intent class is imported.
            val intent = Intent(this, BookingActivity::class.java)

            // Pass the restaurant ID to the booking screen
            intent.putExtra("RESTAURANT_ID", restaurantId)

            startActivity(intent)
        }
    }
}
