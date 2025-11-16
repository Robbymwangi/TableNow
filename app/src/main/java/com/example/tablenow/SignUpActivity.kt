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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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
            spannableString.setSpan(ForegroundColorSpan(getColor(R.color.teal_green)), loginStart, loginEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.loginLink.text = spannableString
        binding.loginLink.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, navigate to Main Activity
                        Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}