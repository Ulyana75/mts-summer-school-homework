package com.ulyanaab.mtshomework.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulyanaab.mtshomework.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        findViewById<View>(R.id.active_home).visibility = View.VISIBLE
        findViewById<View>(R.id.active_profile).visibility = View.INVISIBLE
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.mainFragment)
                    true
                }
                R.id.profile -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

}