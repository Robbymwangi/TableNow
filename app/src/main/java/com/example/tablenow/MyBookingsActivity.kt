package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.databinding.ActivityMyBookingsBinding
import com.example.tablenow.model.Booking

class MybookingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var bookingAdapter: BookingAdapter
    private val bookingsList = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Back Button
        binding.backButton.setOnClickListener { onBackPressed() }

        // 2. Setup RecyclerView and Adapter with Click Listener
        bookingAdapter = BookingAdapter(bookingsList) { booking ->
            // This block runs when a booking card is clicked
            val intent = Intent(this, BookingDetailsActivity::class.java)
            intent.putExtra("BOOKING_ID", booking.id)
            intent.putExtra("RESTAURANT_NAME", booking.title)
            intent.putExtra("BOOKING_DATE", booking.date)
            intent.putExtra("BOOKING_TIME", booking.time)
            intent.putExtra("BOOKING_GUESTS", booking.guests)
            intent.putExtra("IMAGE_URL", booking.imageUrl)
            startActivity(intent)
        }

        binding.bookingsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookingsRecyclerView.adapter = bookingAdapter

        // 3. Create and display realistic placeholder bookings
        createPlaceholderBookings()
    }

    private fun createPlaceholderBookings() {
        bookingsList.clear()

        val booking1 = Booking(
            id = "booking1",
            title = "Saint Germain Bistro",
            guests = "4",
            date = "12 May, Thursday", // Changed format for better extraction
            time = "21:30",
            // FIXED IMAGE URL: Using a reliable picsum photo
            imageUrl = "https://picsum.photos/id/10/800/600"
        )
        val booking2 = Booking(
            id = "booking2",
            title = "Burger Masters",
            guests = "2",
            date = "22 May, Sunday", // Changed format for better extraction
            time = "17:30",
            imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349?w=500&auto=format&fit=crop&q=60"
        )

        bookingsList.addAll(listOf(booking1, booking2))
        bookingAdapter.updateBookings(bookingsList)
    }
}