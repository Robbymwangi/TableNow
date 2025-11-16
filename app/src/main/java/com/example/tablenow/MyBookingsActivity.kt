package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.databinding.ActivityMyBookingsBinding

// In: app/src/main/java/com/example/tablenow/MybookingsActivity.kt
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

        // 2. Create and display the placeholder bookings
        createPlaceholderBookings()
    }

    private fun createPlaceholderBookings() {
        bookingsList.clear()

        // This will now work because Booking.kt has an 'id' field
        val booking1 = Booking(
            id = "booking1",
            title = "The Gourmet Place",
            guests = "2 guests",
            date = "Sat, 25 May 2024",
            time = "19:00",
            imageUrl = "https://picsum.photos/id/20/400/300"
        )
        val booking2 = Booking(
            id = "booking2",
            title = "Sushi Central",
            guests = "4 guests",
            date = "Sun, 26 May 2024",
            time = "20:30",
            imageUrl = "https://picsum.photos/id/40/400/300"
        )

        bookingsList.addAll(listOf(booking1, booking2))
        // This will now work because the function exists in the adapter
        bookingAdapter.updateBookings(bookingsList)
    }
}
