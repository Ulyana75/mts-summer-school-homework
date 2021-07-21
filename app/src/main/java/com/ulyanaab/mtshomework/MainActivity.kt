package com.ulyanaab.mtshomework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.movies.MoviesDataSourceImpl
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.recyclerView.MoviesAdapter
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var recyclerViewMovies: RecyclerView

    private val moviesModel = MoviesModel(MoviesDataSourceImpl())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        recyclerViewGenres = findViewById(R.id.recycler_view_genre)
        val adapter = GenreAdapter(getGenres(), this::adapterGenreListener)
        recyclerViewGenres.adapter = adapter

        recyclerViewMovies = findViewById(R.id.recycler_view_movies)
        val adapter2 = MoviesAdapter(
            moviesModel.getMovies(),
            this::adapterMovieListener,
            calculateImageSizeInPX(this)
        )
        recyclerViewMovies.adapter = adapter2
    }

    private fun getGenres(): MutableList<String> {
        return mutableListOf("боевики", "драмы", "комедии", "артхаус", "мелодрамы", "детективы")
    }

    private fun adapterMovieListener(item: MovieDto) {
        showToast("Вы нажали на фильм ${item.title}")
    }

    private fun adapterGenreListener(item: String) {
        showToast(item)
    }

    private fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> {
                showToast("Пустое сообщение")
            }
            else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}