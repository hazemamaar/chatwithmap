package com.example.tracking.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tracking.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.userBottomNavigationView)
//        val navController = findNavController(R.id.fragment)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_user) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.background = null
    }
}