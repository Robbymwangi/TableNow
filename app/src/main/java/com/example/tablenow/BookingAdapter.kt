package com.example.tablenow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
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

            Glide.with(holder.itemView.context)
                .load(booking.imageUrl)
                .transform(CenterCrop())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image) // Show error icon if loading fails
                .into(bookingImage)
        }
    }

    fun updateBookings(newBookings: List<Booking>) {
        bookingList = newBookings
        notifyDataSetChanged()
    }
}