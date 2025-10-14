package com.example.tablenow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.R

class SkeletonAdapter(private val count: Int) : RecyclerView.Adapter<SkeletonAdapter.SkeletonViewHolder>() {

    class SkeletonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skeleton_card, parent, false)
        return SkeletonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) {
        // No data to bind for a skeleton loader
    }

    override fun getItemCount(): Int {
        return count
    }
}
