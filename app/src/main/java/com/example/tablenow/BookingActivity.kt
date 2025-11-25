package com.example.tablenow

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityBookingBinding
import com.google.android.material.button.MaterialButton
import java.util.Calendar // Needed for Date logic

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private var selectedTime: String? = null

    // 1. ADD THIS VARIABLE to store the date
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: "Restaurant"
        binding.tvRestaurantName.text = restaurantName

        binding.btnBack.setOnClickListener { onBackPressed() }

        // 2. SETUP DATE LOGIC
        // Set default to "Today" so it's not empty if they don't click anything
        val calendar = Calendar.getInstance()
        selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"

        // Listen for clicks on the Calendar
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Note: Month is 0-indexed (0=Jan), so we add 1
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }

        setupTimeSlots()

        binding.btnBookNow.setOnClickListener {
            if (selectedTime == null) {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
            } else {
                val guests = binding.etGuests.text.toString()
                val intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra("RESTAURANT_NAME", restaurantName)
                intent.putExtra("GUESTS", guests)
                intent.putExtra("TIME", selectedTime)

                // 3. PASS THE DATE HERE
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