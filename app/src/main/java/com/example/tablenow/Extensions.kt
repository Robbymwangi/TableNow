package com.example.tablenow

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

// This lets you call imageView.loadImage("url") anywhere in your app
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
        .transform(CenterCrop())
        .placeholder(android.R.drawable.ic_menu_gallery) // Show this while loading
        .error(android.R.drawable.ic_menu_report_image)  // Show this if URL fails
        .into(this)
}