package com.example.tablenow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.databinding.ItemCategoryChipBinding
import com.example.tablenow.model.Category

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(private val binding: ItemCategoryChipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.categoryIcon.setImageResource(category.icon)
            binding.categoryLabel.text = category.name
        }
    }
}
