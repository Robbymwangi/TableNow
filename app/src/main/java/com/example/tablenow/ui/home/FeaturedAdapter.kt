package com.example.tablenow.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.databinding.ItemFeaturedCardBinding

class FeaturedAdapter(private val items: List<String>) : RecyclerView.Adapter<FeaturedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeaturedCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.restaurantName.text = items[position]
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ItemFeaturedCardBinding) : RecyclerView.ViewHolder(binding.root)
}