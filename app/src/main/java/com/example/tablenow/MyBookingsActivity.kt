package com.example.tablenow

import android.os.Bundle
// import android.util.Log // Not needed for mock
// import android.widget.Toast // Not needed for mock
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tablenow.databinding.ActivityMyBookingsBinding
// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.ktx.auth
// import com.google.firebase.firestore.FirebaseFirestore
// import com.google.firebase.firestore.Query
// import com.google.firebase.firestore.ktx.firestore
// import com.google.firebase.ktx.Firebase

class MyBookingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var bookingAdapter: BookingAdapter

    // --- TODO: Uncomment these when integrating Firebase ---
    // private lateinit var auth: FirebaseAuth
    // private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- TODO: Uncomment these when integrating Firebase ---
        // auth = Firebase.auth
        // db = Firebase.firestore

        setupRecyclerView()

        // --- TODO: Uncomment this block when integrating Firebase ---
        /*
        if (auth.currentUser != null) {
            fetchBookingsFromBackend()
        } else {
            // Handle case where user is not logged in
            Toast.makeText(this, "Please log in to see bookings", Toast.LENGTH_SHORT).show()
        }
        */

        // --- This will run the app with mock data ---
        // We call this directly instead of checking for a logged-in user
        fetchBookingsFromBackend()
        // --- End of mock data block ---

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        bookingAdapter = BookingAdapter(emptyList()) // Start with an empty list
        binding.bookingsRecyclerView.adapter = bookingAdapter
        binding.bookingsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchBookingsFromBackend() {

        // --- TODO: Uncomment this block to fetch from Firestore ---
        /*
        val userId = auth.currentUser!!.uid
        db.collection("bookings")
            .whereEqualTo("userId", userId)
            .orderBy("bookingTimestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Convert documents to the Firebase-ready Booking class
                val bookingsList = querySnapshot.toObjects(Booking::class.java)
                bookingAdapter.updateBookings(bookingsList)
            }
            .addOnFailureListener { exception ->
                Log.w("MyBookingsActivity", "Error getting documents: ", exception)
                Toast.makeText(this, "Failed to load bookings", Toast.LENGTH_SHORT).show()
            }
        */

        // --- Mock Data (Active Code) ---
        // This data matches the simple string-based Booking class
        val mockBookingsData = listOf(
            Booking(
                title = "Saint Germain Bistro",
                date = "Thursday, 12 May 2022",
                time = "21:30",
                guests = "4 guests",
                imageUrl = "https://example.com/images/saint_germain.png" // Use a real URL
            ),
            Booking(
                title = "Burger Masters",
                date = "Sunday, 22 May 2022",
                time = "17:30",
                guests = "2 guests",
                imageUrl = "https://example.com/images/burger_masters.png" // Use a real URL
            )
        )

        // Update the adapter with the mock data
        bookingAdapter.updateBookings(mockBookingsData)
    }
}