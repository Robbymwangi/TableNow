package com.example.tablenow

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

        // FIX: Hide the default ActionBar to remove the duplicate "TableNow" title
        supportActionBar?.hide()

        // 1. Setup Back Button
        binding.backButton.setOnClickListener { onBackPressed() }

        // 2. Get Data
        val restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: "Restaurant"
        val bookingDate = intent.getStringExtra("BOOKING_DATE") ?: ""
        val bookingTime = intent.getStringExtra("BOOKING_TIME") ?: ""
        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: ""

        // 3. Populate Views
        binding.tvRestaurantName.text = restaurantName

        // DATE FIX: Changed getOrNull(1) to getOrNull(0)
        // Logic: "12 May, Thursday" -> split gives ["12 May", " Thursday"]
        // We want index 0 ("12 May")
        val cleanDate = bookingDate.split(",").getOrNull(0)?.trim() ?: bookingDate

        binding.tvBookingDate.text = cleanDate
        binding.tvBookingTime.text = bookingTime

        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.ivRestaurantImage)

        // 4. Cancel Action
        binding.btnCancelBooking.setOnClickListener {
            Toast.makeText(this, "Booking cancelled", Toast.LENGTH_SHORT).show()
            finish() // Close screen
        }
    }
}