package com.example.tablenow

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityBookingBinding
import com.google.android.material.button.MaterialButton
import java.util.Calendar

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private var selectedTime: String? = null
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: "Restaurant"
        binding.tvRestaurantName.text = restaurantName

        binding.btnBack.setOnClickListener { onBackPressed() }

        // Date Logic
        val calendar = Calendar.getInstance()
        selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }

        setupTimeSlots()

        // --- UPDATED VALIDATION LOGIC IS HERE ---
        binding.btnBookNow.setOnClickListener {
            val guestInput = binding.etGuests.text.toString()
            // Convert text to a number safely. If it's empty or invalid, treat it as 0.
            val guestCount = guestInput.toIntOrNull() ?: 0

            if (selectedTime == null) {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
            } else if (guestCount < 1) {
                // This blocks 0, negative numbers, or empty inputs
                Toast.makeText(this, "Please enter at least 1 guest", Toast.LENGTH_SHORT).show()
            } else {
                // Success! Proceed to next screen
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("RESTAURANT_NAME", restaurantName)
                intent.putExtra("GUESTS", guestInput)
                intent.putExtra("TIME", selectedTime)
                intent.putExtra("DATE", selectedDate)
                startActivity(intent)
            }
        }
    }

    private fun setupTimeSlots() {
        val buttons = listOf(
            binding.time1800, binding.time1900, binding.time1930,
            binding.time2030, binding.time2100, binding.time2130, binding.time2200
        )

        val colorWhite = ColorStateList.valueOf(Color.WHITE)
        val colorBlue = ColorStateList.valueOf(Color.parseColor("#0D1B3E"))
        val colorBlack = Color.BLACK
        val colorStroke = ColorStateList.valueOf(Color.parseColor("#E0E0E0"))

        for (btn in buttons) {
            btn.setOnClickListener { clickedBtn ->
                buttons.forEach {
                    it.backgroundTintList = colorWhite
                    it.setTextColor(colorBlack)
                    it.strokeWidth = 3
                    it.strokeColor = colorStroke
                }

                val selected = clickedBtn as MaterialButton
                selected.backgroundTintList = colorBlue
                selected.setTextColor(Color.WHITE)
                selected.strokeWidth = 0

                selectedTime = selected.text.toString()
            }
        }
    }
}