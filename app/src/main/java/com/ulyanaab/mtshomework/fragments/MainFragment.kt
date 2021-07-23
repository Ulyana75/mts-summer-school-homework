package com.ulyanaab.mtshomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.MoviesModel
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.utilits.calculateImageSizeInPX
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.movies.MoviesDataSourceWithDelay
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.recyclerView.MoviesAdapter
import com.ulyanaab.mtshomework.utilits.exceptionHandler
import com.ulyanaab.mtshomework.utilits.replaceFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class MainFragment : Fragment() {

    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var recyclerViewMovies: RecyclerView

    private val moviesModel = MoviesModel(MoviesDataSourceWithDelay())

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
            val movies = moviesModel.getMovies()
            val adapter2 = MoviesAdapter(
                movies,
                this@MainFragment::adapterMovieListener,
                calculateImageSizeInPX(requireContext())
            )
            recyclerViewMovies.adapter = adapter2
        }
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

}