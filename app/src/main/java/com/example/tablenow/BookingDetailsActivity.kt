package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tablenow.databinding.ActivityBookingDetailsBinding

class BookingDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the default ActionBar
        supportActionBar?.hide()

        // 1. Setup Back Button
        binding.backButton.setOnClickListener { onBackPressed() }

        // 2. Get Data from Intent
        val restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: "Restaurant"
        val bookingDate = intent.getStringExtra("BOOKING_DATE") ?: ""
        val bookingTime = intent.getStringExtra("BOOKING_TIME") ?: ""
        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: ""

        // 3. Populate Views
        binding.tvRestaurantName.text = restaurantName

        // Extract only the date (e.g. "12 May") from string "Thursday, 12 May 2022"
        val cleanDate = bookingDate.split(",").getOrNull(0)?.trim() ?: bookingDate

        binding.tvBookingDate.text = cleanDate
        binding.tvBookingTime.text = bookingTime

        // Load the image passed from the previous screen
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.ivRestaurantImage)

        // 4. LINKED: Navigate to CancelledActivity
        binding.btnCancelBooking.setOnClickListener {
            val intent = Intent(this, CancelledActivity::class.java)
            startActivity(intent)
            // We use finish() so the user can't press 'Back' and return to this details screen
            // after cancelling.
            finish()
        }

        // Optional: Add logic for "Contact restaurant" or "View menu" here if needed
    }
}