package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- 1. RECEIVE AND DISPLAY BOOKING DETAILS ---
        // Get the data that was passed from BookingActivity
        val guests = intent.getStringExtra("GUESTS")
        val date = intent.getStringExtra("DATE")
        val time = intent.getStringExtra("TIME")

        // Create a user-friendly confirmation message
        val confirmationMessage = "You have successfully booked a table for $guests guests on day $date at $time."

        // Display the message in your TextView.
        // **IMPORTANT**: Make sure your TextView in activity_confirmation.xml has the id "confirmationDetailsTextView"
        binding.confirmedText.text = confirmationMessage

        // --- 2. SET UP BUTTON NAVIGATION ---
        binding.homepageButton.setOnClickListener {
            // Create an intent to go back to the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // These flags clear the other activities (Booking, Confirmation) from the back stack.
            // This provides a clean navigation flow for the user.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.myBookingsButton.setOnClickListener {
            // Create an intent to go to the MyBookingsActivity
            val intent = Intent(this, MybookingsActivity::class.java)
            startActivity(intent)
        }
    }
}
