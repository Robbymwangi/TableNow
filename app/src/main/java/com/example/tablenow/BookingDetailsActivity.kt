package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tablenow.databinding.ActivityBookingDetailsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookingDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingDetailsBinding
    private val db = Firebase.firestore // Initialize Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backButton.setOnClickListener { onBackPressed() }

        // 1. Get Data (Including the Document ID)
        val bookingId = intent.getStringExtra("BOOKING_ID") ?: ""
        val restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: "Restaurant"
        val bookingDate = intent.getStringExtra("BOOKING_DATE") ?: ""
        val bookingTime = intent.getStringExtra("BOOKING_TIME") ?: ""
        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: ""

        // 2. Populate Views
        binding.tvRestaurantName.text = restaurantName
        val cleanDate = bookingDate.split(",").getOrNull(0)?.trim() ?: bookingDate
        binding.tvBookingDate.text = cleanDate
        binding.tvBookingTime.text = bookingTime

        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(binding.ivRestaurantImage)

        // 3. CANCEL LOGIC (Live Delete)
        binding.btnCancelBooking.setOnClickListener {
            if (bookingId.isNotEmpty()) {
                // Disable button to prevent double clicks
                binding.btnCancelBooking.isEnabled = false

                db.collection("bookings").document(bookingId)
                    .delete()
                    .addOnSuccessListener {
                        // Success: Navigate to Cancelled Screen
                        val intent = Intent(this, CancelledActivity::class.java)
                        startActivity(intent)
                        finish() // Close this details screen
                    }
                    .addOnFailureListener { e ->
                        // Error
                        binding.btnCancelBooking.isEnabled = true
                        Toast.makeText(this, "Error cancelling: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Error: Booking ID not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}