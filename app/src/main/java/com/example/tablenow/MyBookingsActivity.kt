package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.databinding.ActivityMyBookingsBinding
import com.example.tablenow.model.Booking
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MybookingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var bookingAdapter: BookingAdapter
    private val bookingsList = mutableListOf<Booking>()

    // Firebase
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { onBackPressed() }

        // 1. Setup Adapter
        bookingAdapter = BookingAdapter(bookingsList) { booking ->
            // Click Listener: Open Details
            val intent = Intent(this, BookingDetailsActivity::class.java)
            intent.putExtra("BOOKING_ID", booking.id) // Pass the real Firestore ID
            intent.putExtra("RESTAURANT_NAME", booking.title)
            intent.putExtra("BOOKING_DATE", booking.date)
            intent.putExtra("BOOKING_TIME", booking.time)
            intent.putExtra("IMAGE_URL", booking.imageUrl)
            startActivity(intent)
        }

        binding.bookingsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookingsRecyclerView.adapter = bookingAdapter

        // 2. Check Login & Fetch Data
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fetchRealtimeBookings(currentUser.uid)
        } else {
            // Handle guest/logged out state
            binding.emptyStateLayout.visibility = View.VISIBLE
            binding.bookingsRecyclerView.visibility = View.GONE
        }
    }

    private fun fetchRealtimeBookings(userId: String) {
        // Listen to changes in the 'bookings' collection for this user
        db.collection("bookings")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("Firestore", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    bookingsList.clear()

                    for (document in snapshots) {
                        // Map Firestore fields to Booking object
                        // The strings inside "" must match your database fields EXACTLY
                        val booking = Booking(
                            id = document.id, // The document ID (e.g., "ikKHv5...")
                            title = document.getString("restaurantName") ?: "Unknown",
                            guests = document.getString("guests") ?: "0",
                            date = document.getString("date") ?: "",
                            time = document.getString("time") ?: "",
                            imageUrl = document.getString("imageUrl") ?: ""
                        )
                        bookingsList.add(booking)
                    }

                    // Update UI
                    bookingAdapter.updateBookings(bookingsList)

                    // Toggle Empty State
                    if (bookingsList.isEmpty()) {
                        binding.emptyStateLayout.visibility = View.VISIBLE
                        binding.bookingsRecyclerView.visibility = View.GONE
                    } else {
                        binding.emptyStateLayout.visibility = View.GONE
                        binding.bookingsRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
    }
}