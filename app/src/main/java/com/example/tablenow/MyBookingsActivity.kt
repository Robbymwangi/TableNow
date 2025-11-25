package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.databinding.ActivityMyBookingsBinding
import com.example.tablenow.model.Booking // <--- CRITICAL IMPORT: Points to the correct model

class MybookingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var bookingAdapter: BookingAdapter
    private val bookingsList = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup RecyclerView and Adapter
        bookingAdapter = BookingAdapter(bookingsList)
        binding.bookingsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookingsRecyclerView.adapter = bookingAdapter

        // 2. Setup Back Button
        // (If your layout has a back button with id 'backButton', uncomment this)
        // binding.backButton.setOnClickListener { onBackPressed() }

        // 3. Create and display the placeholder bookings
        createPlaceholderBookings()
    }

    private fun createPlaceholderBookings() {
        bookingsList.clear()

        val booking1 = Booking(
            id = "booking1",
            title = "The Gourmet Place",
            guests = "2", // Removed "guests" word if your model expects just numbers, or keep if string
            date = "Sat, 25 May 2024",
            time = "19:00",
            imageUrl = "https://picsum.photos/id/20/400/300"
        )
        val booking2 = Booking(
            id = "booking2",
            title = "Sushi Central",
            guests = "4",
            date = "Sun, 26 May 2024",
            time = "20:30",
            imageUrl = "https://picsum.photos/id/40/400/300"
        )

        bookingsList.addAll(listOf(booking1, booking2))
        bookingAdapter.updateBookings(bookingsList)
    }
}