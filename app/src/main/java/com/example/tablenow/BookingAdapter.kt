package com.example.tablenow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tablenow.databinding.ItemBookingBinding
import com.example.tablenow.model.Booking

class BookingAdapter(
    private var bookingList: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        holder.binding.apply {
            bookingTitle.text = booking.title
            bookingGuests.text = "${booking.guests} Guests" // Added "Guests" text for clarity
            bookingDate.text = booking.date
            bookingTime.text = booking.time

            // FIXED: Switched from Coil to Glide for consistency
            Glide.with(holder.itemView.context)
                .load(booking.imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(bookingImage)

            cancelButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, CancelledActivity::class.java)
                // Passing the restaurant name so the Cancelled screen knows what was cancelled
                intent.putExtra("RESTAURANT_NAME", booking.title)
                context.startActivity(intent)
            }
        }
    }

    fun updateBookings(newBookings: List<Booking>) {
        bookingList = newBookings
        notifyDataSetChanged()
    }
}