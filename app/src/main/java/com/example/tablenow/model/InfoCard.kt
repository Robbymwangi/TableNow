package com.example.tablenow.model

data class InfoCard(
    val name: String,
    val category: String,
    val rating: String,
    val imageUrl: String,
    val isFeatured: Boolean = false
)