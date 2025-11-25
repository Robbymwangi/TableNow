package com.example.tablenow

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityBookingBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private var selectedTime: String? = null
    private var selectedDate: String = ""

    // Firebase
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantName = intent.getStringExtra("RESTAURANT_NAME") ?: "Restaurant"
        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: "" // Get the image passed from details

        binding.tvRestaurantName.text = restaurantName
        binding.btnBack.setOnClickListener { onBackPressed() }

        // Date Setup
        val calendar = Calendar.getInstance()
        // GUARDRAIL: Prevent selecting past dates
        // This creates a "floor" so users can't scroll back before today
        binding.calendarView.minDate = System.currentTimeMillis() - 1000 
        selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }

        setupTimeSlots()

        // --- BOOKING LOGIC ---
        binding.btnBookNow.setOnClickListener {
            val guestInput = binding.etGuests.text.toString()
            val guestCount = guestInput.toIntOrNull() ?: 0
            val user = auth.currentUser

            // Validation
            if (user == null) {
                Toast.makeText(this, "You must be logged in to book", Toast.LENGTH_SHORT).show()
                // Optional: Redirect to LoginActivity here
            } else if (selectedTime == null) {
                Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show()
            } else if (guestCount < 1) {
                Toast.makeText(this, "Please enter at least 1 guest", Toast.LENGTH_SHORT).show()
            } else {
                // 1. SHOW LOADING & DISABLE INTERACTION
                binding.progressBar.visibility = View.VISIBLE
                binding.btnBookNow.isEnabled = false
                binding.btnBookNow.text = "Processing..."

                // 2. Create Data Object
                val bookingData = hashMapOf(
                    "userId" to user.uid,
                    "restaurantName" to restaurantName,
                    "date" to selectedDate,
                    "time" to selectedTime,
                    "guests" to guestInput,
                    "imageUrl" to imageUrl,
                    "status" to "confirmed",
                    "timestamp" to com.google.firebase.Timestamp.now()
                )

                // 3. Save to Firestore
                db.collection("bookings")
                    .add(bookingData)
                    .addOnSuccessListener { documentReference ->
                        // Success! Go to confirmation
                        binding.progressBar.visibility = View.GONE
                        val intent = Intent(this, ConfirmationActivity::class.java)
                        intent.putExtra("RESTAURANT_NAME", restaurantName)
                        intent.putExtra("GUESTS", guestInput)
                        intent.putExtra("TIME", selectedTime)
                        intent.putExtra("DATE", selectedDate)
                        startActivity(intent)
                        finish() // Close this screen
                    }
                    .addOnFailureListener { e ->
                        // 3. RESET ON FAILURE
                        binding.progressBar.visibility = View.GONE
                        binding.btnBookNow.isEnabled = true
                        binding.btnBookNow.text = "Book now"
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
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