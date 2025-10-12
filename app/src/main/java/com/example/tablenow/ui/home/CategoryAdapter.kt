package com.example.tablenow.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.databinding.ItemCategoryCardBinding

class CategoryAdapter(private val items: List<String>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.categoryLabel.text = items[position]
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ItemCategoryCardBinding) : RecyclerView.ViewHolder(binding.root)
}