package com.example.tablenow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tablenow.databinding.ItemRestaurantBinding

class RestaurantAdapter(private val restaurantList: List<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun getItemCount() = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]

        holder.binding.restaurantNameTextView.text = restaurant.name
        holder.binding.ratingTextView.text = restaurant.rating // Updated to use ratingTextView and rating
        holder.binding.restaurantImageView.load(restaurant.imageUrl) {
            crossfade(true)
        }

        // --- Navigation Logic ---
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, RestaurantDetailActivity::class.java)
            intent.putExtra("RESTAURANT_ID", restaurant.id)
            context.startActivity(intent)
        }
    }
}
