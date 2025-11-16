package com.example.tablenow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tablenow.databinding.ItemBookingBinding

class BookingAdapter(
    private var bookingList: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    /**
     * Inner ViewHolder class that holds the binding for the item layout.
     * This avoids repeated calls to findViewById.
     */
    inner class BookingViewHolder(val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Called when RecyclerView needs a new ViewHolder. It inflates the
     * item_booking.xml layout and creates a ViewHolder for it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }

    /**
     * Returns the total number of items in the list.
     */
    override fun getItemCount(): Int {
        return bookingList.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method updates the contents of the ViewHolder's item view.
     */
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        // Get the booking object for the current position
        val booking = bookingList[position]

        // Use the binding to access the views and set the data
        holder.binding.apply {
            bookingTitle.text = booking.title
            bookingGuests.text = booking.guests
            bookingDate.text = booking.date
            bookingTime.text = booking.time

            // Use the Coil library to load the image from a URL
            bookingImage.load(booking.imageUrl) {
                crossfade(true) // Optional: for a smooth fade-in effect
                // placeholder(R.drawable.your_placeholder_image) // Optional: if you have a placeholder
                // error(R.drawable.your_error_image) // Optional: if you have an error image
            }

            // Set up the click listener for the cancel button
            cancelButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, CancelledActivity::class.java)

                // You could pass data to the CancelledActivity if needed, like this:
                // intent.putExtra("BOOKING_ID", booking.id)
                // intent.putExtra("RESTAURANT_NAME", booking.title)

                context.startActivity(intent)
            }
        }
    }

    /**
     * A helper function to update the list of bookings in the adapter
     * from the activity and refresh the RecyclerView.
     */
    fun updateBookings(newBookings: List<Booking>) {
        bookingList = newBookings
        notifyDataSetChanged() // Notifies the adapter that the data set has changed
    }
}
