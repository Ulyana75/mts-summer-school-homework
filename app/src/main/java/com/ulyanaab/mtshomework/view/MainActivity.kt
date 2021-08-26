package com.ulyanaab.mtshomework.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.work.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.utilities.MyWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initViews()
        createWorker()
    }

    private fun createWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .build()

        val workRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<MyWorker>(
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "PeriodicMyWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun initViews() {
        findViewById<View>(R.id.active_home).visibility = View.VISIBLE
        findViewById<View>(R.id.active_profile).visibility = View.INVISIBLE
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_profileFragment_to_mainFragment)
                    false
                }
                R.id.profile -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_profileFragment)
                    false
                }
                else -> true
            }
        }
    }

}