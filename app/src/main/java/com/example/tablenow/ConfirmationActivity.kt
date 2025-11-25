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

        // --- 1. RECEIVE BOOKING DETAILS ---
        // We use ?: "..." to provide a default value if the data is missing
        val guests = intent.getStringExtra("GUESTS") ?: "2"
        val date = intent.getStringExtra("DATE") ?: "Today"
        val time = intent.getStringExtra("TIME") ?: "19:00"

        // --- 2. DISPLAY MESSAGE ---
        val confirmationMessage = "Your reservation is confirmed!."

        binding.confirmedText.text = confirmationMessage

        // --- 3. NAVIGATION ---
        binding.homepageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.myBookingsButton.setOnClickListener {
            val intent = Intent(this, MybookingsActivity::class.java)
            startActivity(intent)
        }
    }
}