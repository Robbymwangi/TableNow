package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityConfirmationBinding // This is generated

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and set the content view using View Binding
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners
        binding.homepageButton.setOnClickListener {
            // Handle "Go to homepage" click
            // e.g., startActivity(Intent(this, HomeActivity::class.java))
            // finish() // Close this activity
        }

        binding.myBookingsButton.setOnClickListener {
            // Handle "My bookings" click
            // e.g., startActivity(Intent(this, MyBookingsActivity::class.java))
            // finish() // Close this activity
        }
    }
}