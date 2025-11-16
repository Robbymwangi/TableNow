package com.example.tablenow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tablenow.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This line is important because your fragment_home.xml has its own toolbar.
        // We set the main activity's toolbar here.
        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Define which screens are "top-level". These will show the hamburger menu
        // instead of a back arrow.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home // Assuming 'nav_home' is the ID of your HomeFragment in mobile_navigation.xml
                // Add other top-level destinations here, e.g., R.id.nav_my_bookings
            ), drawerLayout
        )

        // Connects the action bar (the top toolbar) to the navigation controller.
        // This handles showing the correct title and the back/hamburger buttons.
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Connects the navigation drawer menu to the navigation controller.
        // This handles clicks on menu items to navigate to different fragments.
        navView.setupWithNavController(navController)
    }

    // This function is essential for making the Up (back) button and the hamburger menu work correctly.
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
