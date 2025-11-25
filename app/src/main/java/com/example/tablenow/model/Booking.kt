package com.example.tablenow.model

data class Booking(
    val id: String,
    val title: String,      // Restaurant Name
    val guests: String,
    val date: String,
    val time: String,
    val imageUrl: String
)