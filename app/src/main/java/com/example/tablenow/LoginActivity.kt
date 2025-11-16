package com.example.tablenow

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Make the "Sign up" part of the text bold and colored
        val signUpText = "Don\'t have an account? Sign up"
        val spannableString = SpannableString(signUpText)

        val signUpStart = signUpText.indexOf("Sign up")
        val signUpEnd = signUpStart + "Sign up".length

        if (signUpStart != -1) {
            spannableString.setSpan(StyleSpan(android.graphics.Typeface.BOLD), signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(getColor(R.color.teal_green)), signUpStart, signUpEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.signUpLink.text = spannableString

        binding.loginButton.setOnClickListener {
            // Navigate to MainActivity, which hosts HomeFragment as its start destination.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish LoginActivity so user can\'t go back to it
        }

        binding.signUpLink.setOnClickListener {
            // Navigate to SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            // No finish() here, as we might want to come back to LoginActivity from SignUpActivity
        }
    }
}