package com.example.tablenow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load // <-- Import Coil
import com.example.tablenow.databinding.ItemBookingBinding
// import java.text.SimpleDateFormat // Not needed for mock data
// import java.util.Locale // Not needed for mock data

class BookingAdapter(
    private var bookingList: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    // This holds the views for a single item
    inner class BookingViewHolder(val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root)

    // This creates a new view holder when the list needs one
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    // This returns the total number of items in the list
    override fun getItemCount(): Int {
        return bookingList.size
    }

    // This is the most important function: it binds your data to the views
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        // --- Bind the simple string data (from mock data) ---
        holder.binding.bookingTitle.text = booking.title
        holder.binding.bookingGuests.text = booking.guests
        holder.binding.bookingDate.text = booking.date
        holder.binding.bookingTime.text = booking.time

        /*
        // --- TODO: Uncomment this when using the Firebase Booking class ---

        // Format the Int into a string
        holder.binding.bookingGuests.text = "${booking.guests} guests"

        // Format the Timestamp into Date and Time strings
        booking.bookingTimestamp?.let { timestamp ->
            val sdfDate = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
            val javaDate = timestamp.toDate()

            holder.binding.bookingDate.text = sdfDate.format(javaDate)
            holder.binding.bookingTime.text = sdfTime.format(javaDate)
        }
        */

        // --- Use Coil (from your Gradle file) to load the image ---
        holder.binding.bookingImage.load(booking.imageUrl) {
            // You can add placeholders here if you want
            // placeholder(R.drawable.ic_placeholder)
            // error(R.drawable.ic_error)
        }
    }

    // This function is called from your Activity to update the list with new data
    fun updateBookings(newBookings: List<Booking>) {
        bookingList = newBookings
        notifyDataSetChanged() // This tells the adapter to refresh the list
    }
}