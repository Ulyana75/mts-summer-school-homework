package com.ulyanaab.mtshomework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.view.fragments.MainFragment
import com.ulyanaab.mtshomework.view.fragments.ProfileFragment
import com.ulyanaab.mtshomework.utilits.replaceFragment

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
        replaceFragment(this, MainFragment(), false)
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(this, MainFragment(), false)
                    findViewById<View>(R.id.active_home).visibility = View.VISIBLE
                    findViewById<View>(R.id.active_profile).visibility = View.INVISIBLE
                    true
                }
                R.id.profile -> {
                    replaceFragment(this, ProfileFragment(), false)
                    findViewById<View>(R.id.active_home).visibility = View.INVISIBLE
                    findViewById<View>(R.id.active_profile).visibility = View.VISIBLE
                    true
                }
                else -> false
            }
        }
    }

}