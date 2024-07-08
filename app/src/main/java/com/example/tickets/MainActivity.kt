package com.example.tickets

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.tickets.databinding.ActivityMainBinding
import com.example.tickets.utils.CITY_FROM
import com.example.tickets.utils.CITY_TO
import com.example.tickets.utils.SETTINGS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setThreadPolicy()
        setNavigation()
    }

    private fun setThreadPolicy() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun setNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_fragment)

        binding.navView.setupWithNavController(navController)

        binding.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.tickets_nav -> {
                    navController.navigate(R.id.tickets_fragment)
                }
                R.id.hotels_nav -> {
                    navController.navigate(R.id.hotels_fragment)
                }
                R.id.profile_nav -> {
                    navController.navigate(R.id.profile_fragment)
                }
                R.id.shorter_nav -> {
                    navController.navigate(R.id.shorter_fragment)
                }
                R.id.subscriptions_nav -> {
                    navController.navigate(R.id.subscriptions_fragment)
                }
            }
            true
        }
        binding.navView.itemIconTintList = null
    }

    override fun onPause() {
        super.onPause()

        val settings = application.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
        settings.edit()
            .putString(CITY_FROM, "")
            .putString(CITY_TO, "")
            .apply()
    }
}