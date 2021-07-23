package com.ulyanaab.mtshomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ulyanaab.mtshomework.MoviesModel
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.utilits.calculateImageSizeInPX
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.movies.MoviesDataSourceWithDelay
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.recyclerView.MoviesAdapter
import com.ulyanaab.mtshomework.utilits.DISTANCE_FOR_SWIPE_REFRESH
import com.ulyanaab.mtshomework.utilits.exceptionHandler
import com.ulyanaab.mtshomework.utilits.replaceFragment
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException


class MainFragment : Fragment() {

    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var recyclerViewMovies: RecyclerView

    private val moviesModel = MoviesModel(MoviesDataSourceWithDelay())
    private var movies = mutableListOf<MovieDto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        recyclerViewGenres = requireView().findViewById(R.id.recycler_view_genre)
        val adapter = GenreAdapter(getGenres(), this::adapterGenreListener)
        recyclerViewGenres.adapter = adapter

        recyclerViewMovies = requireView().findViewById(R.id.recycler_view_movies)

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            moviesModel.getMovies().forEach {
                movies.add(it)
            }
            withContext(Dispatchers.Main) {
                recyclerViewMovies.adapter?.notifyDataSetChanged()
            }
        }

        val adapter2 = MoviesAdapter(
            movies,
            this@MainFragment::adapterMovieListener,
            calculateImageSizeInPX(requireContext())
        )
        recyclerViewMovies.adapter = adapter2

        val swipeRefreshLayout = requireView().findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            updateMoviesList()
            swipeRefreshLayout.isRefreshing = false
        }
        swipeRefreshLayout.setDistanceToTriggerSync(DISTANCE_FOR_SWIPE_REFRESH)
    }

    private fun getGenres(): MutableList<String> {
        return mutableListOf("боевики", "драмы", "комедии", "артхаус", "мелодрамы", "детективы")
    }

    private fun adapterMovieListener(item: MovieDto) {
        replaceFragment(requireActivity(), MovieDetailsFragment.newInstance(item), true)
    }

    private fun adapterGenreListener(item: String) {
        showToast(item)
    }

    private fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> {
                showToast("Пустое сообщение")
            }
            else -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMoviesList() {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            delay(2)
            movies.add(0,
                MovieDto(
                    title = "Круэлла",
                    description = "Невероятно одаренная мошенница по имени Эстелла решает сделать себе имя в мире моды.",
                    rateScore = 4,
                    ageRestriction = 12,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/hUfyYGP9Xf6cHF9y44JXJV3NxZM.jpg"
                )
            )
            movies.add(0,
                MovieDto(
                    title = "Чёрная вдова",
                    description = "Чёрной Вдове придется вспомнить о том, что было в её жизни задолго до присоединения к команде Мстителей",
                    rateScore = 3,
                    ageRestriction = 16,
                    imageUrl = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mbtN6V6y5kdawvAkzqN4ohi576a.jpg"
                ),
            )
            withContext(Dispatchers.Main) {
                recyclerViewMovies.adapter?.notifyDataSetChanged()
            }
        }
    }


}