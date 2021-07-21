package com.ulyanaab.mtshomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.fragments.MainFragment
import com.ulyanaab.mtshomework.fragments.ProfileFragment
import com.ulyanaab.mtshomework.movies.MoviesDataSourceImpl
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.recyclerView.MoviesAdapter
import com.ulyanaab.mtshomework.utilits.calculateImageSizeInPX
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