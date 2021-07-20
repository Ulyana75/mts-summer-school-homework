package com.ulyanaab.mtshomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.fragments.MainFragment
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
        replaceFragment(this, MainFragment(), false)
    }

}