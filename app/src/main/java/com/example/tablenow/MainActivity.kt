package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tablenow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar and navigation are now handled within HomeFragment's layout
        // to achieve the collapsing header effect.
        // MainActivity is now just a container for the NavHostFragment.
    }
}
