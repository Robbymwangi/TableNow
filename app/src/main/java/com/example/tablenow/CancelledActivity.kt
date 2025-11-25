package com.example.tablenow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityCancelledBinding
import com.example.tablenow.MybookingsActivity

class CancelledActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCancelledBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelledBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide Action Bar
        supportActionBar?.hide()

        binding.homepageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Clear the back stack so they start fresh at Home
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.seeOtherBookingsButton.setOnClickListener {
            val intent = Intent(this, MybookingsActivity::class.java)
            startActivity(intent)
        }
    }
}