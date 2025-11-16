package com.example.tablenow

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLoginLink()
        setupSignUpButton()
    }

    private fun setupLoginLink() {
        val fullText = "Already have an account? Login"
        val spannableString = SpannableString(fullText)

        val loginClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                finish() // Finish SignUpActivity so user can't go back to it from LoginActivity
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }

        val loginStart = fullText.indexOf("Login")
        val loginEnd = loginStart + "Login".length

        if (loginStart != -1) {
            spannableString.setSpan(loginClickableSpan, loginStart, loginEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(StyleSpan(Typeface.BOLD), loginStart, loginEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#1A746B")), loginStart, loginEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.loginLink.text = spannableString
        binding.loginLink.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            // Implement your sign-up logic here
            // For now, let's navigate to LoginActivity after successful sign-up
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finish SignUpActivity so user can't go back to it from LoginActivity
        }
    }
}