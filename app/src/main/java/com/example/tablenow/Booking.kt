package com.example.tablenow

// import com.google.firebase.Timestamp

/**
 * This is the active data class.
 * It's "backend-ready" and uses simple strings, which we can supply with mock data.
 */
data class Booking(
    val title: String,
    val date: String,
    val time: String,
    val guests: String,
    val imageUrl: String
)

/*
// --- TODO: Use this class when you integrate Firebase ---
// This version is optimized for Firestore
data class Booking(
    // Firestore needs an empty constructor to deserialize data
    val title: String = "",
    val userId: String = "", // Crucial for querying!
    val imageUrl: String = "",
    val guests: Int = 0,
    val bookingTimestamp: Timestamp? = null
)
*/