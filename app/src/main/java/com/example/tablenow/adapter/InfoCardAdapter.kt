package com.example.tablenow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tablenow.R
import com.example.tablenow.model.InfoCard
import android.widget.TextView

class InfoCardAdapter(
    private val infoCards: List<InfoCard>,
    private val onClick: (InfoCard, ImageView, String) -> Unit
) : RecyclerView.Adapter<InfoCardAdapter.InfoCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_card, parent, false)
        return InfoCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoCardViewHolder, position: Int) {
        val infoCard = infoCards[position]
        holder.bind(infoCard)
        val transitionName = "restaurant_image_${position}"
        holder.itemView.findViewById<ImageView>(R.id.restaurant_image).transitionName = transitionName
        holder.itemView.setOnClickListener {
            onClick(infoCard, holder.itemView.findViewById(R.id.restaurant_image), transitionName)
        }
    }

    override fun getItemCount(): Int = infoCards.size

    class InfoCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val restaurantImage: ImageView = itemView.findViewById(R.id.restaurant_image)
        private val restaurantName: TextView = itemView.findViewById(R.id.restaurant_name)
        private val restaurantCategory: TextView = itemView.findViewById(R.id.restaurant_category)
        private val restaurantRating: TextView = itemView.findViewById(R.id.restaurant_rating)
        private val featuredChip: TextView = itemView.findViewById(R.id.featured_chip)

        fun bind(infoCard: InfoCard) {
            Glide.with(itemView.context)
                .load(infoCard.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(restaurantImage)
            restaurantName.text = infoCard.name
            restaurantCategory.text = infoCard.category
            restaurantRating.text = infoCard.rating
            featuredChip.visibility = if (infoCard.isFeatured) View.VISIBLE else View.GONE
        }
    }
}