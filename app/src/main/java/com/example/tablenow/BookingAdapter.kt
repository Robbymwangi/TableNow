package com.example.tablenow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.databinding.ItemBookingBinding
import com.example.tablenow.model.Booking

class BookingAdapter(
    private var bookingList: List<Booking>,
    private val onBookingClick: (Booking) -> Unit // Add click listener to constructor
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    inner class BookingViewHolder(val binding: ItemBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // Set click listener on the entire item view
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBookingClick(bookingList[position])
                }
            }
        }
    }

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
            bookingDate.text = booking.date
            bookingTime.text = booking.time
            bookingGuests.text = "${booking.guests} guests"

            bookingImage.loadImage(booking.imageUrl)
        }
    }

    fun updateBookings(newBookings: List<Booking>) {
        bookingList = newBookings
        notifyDataSetChanged()
    }
}