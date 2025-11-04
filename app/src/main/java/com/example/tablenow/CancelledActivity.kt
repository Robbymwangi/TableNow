package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityCancelledBinding // This is generated

class CancelledActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCancelledBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and set the content view using View Binding
        binding = ActivityCancelledBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the button
        binding.homepageButton.setOnClickListener {
            // Handle "Go to homepage" click
            // For example, you might want to close this activity:
            finish()

            // Or navigate to your main activity:
            // val intent = Intent(this, MainActivity::class.java)
            // intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            // startActivity(intent)
        }
    }
}