package com.ulyanaab.mtshomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ulyanaab.mtshomework.MoviesModel
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.utilits.calculateImageSizeInPX
import com.ulyanaab.mtshomework.dto.MovieDto
import com.ulyanaab.mtshomework.movies.MoviesDataSourceWithDelay
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.recyclerView.MoviesAdapter
import com.ulyanaab.mtshomework.recyclerView.diffUtil.MoviesDiffUtilCallback
import com.ulyanaab.mtshomework.utilits.DISTANCE_FOR_SWIPE_REFRESH
import com.ulyanaab.mtshomework.utilits.exceptionHandler
import com.ulyanaab.mtshomework.utilits.replaceFragment
import kotlinx.coroutines.*


class MainFragment : Fragment() {

    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val moviesModel = MoviesModel(MoviesDataSourceWithDelay())
    private var movies = listOf<MovieDto>()

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
        initRecyclerViews()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        swipeRefreshLayout = requireView().findViewById(R.id.swipe_refresh)

        swipeRefreshLayout.setOnRefreshListener {
            updateMoviesList()
        }

        swipeRefreshLayout.setDistanceToTriggerSync(DISTANCE_FOR_SWIPE_REFRESH)
    }

    private fun initRecyclerViews() {
        recyclerViewGenres = requireView().findViewById(R.id.recycler_view_genre)
        val adapter = GenreAdapter(getGenres(), this::adapterGenreListener)
        recyclerViewGenres.adapter = adapter

        recyclerViewMovies = requireView().findViewById(R.id.recycler_view_movies)

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            movies = moviesModel.getMovies()

            withContext(Dispatchers.Main) {
                moviesAdapter = MoviesAdapter(
                    movies,
                    this@MainFragment::adapterMovieListener,
                    calculateImageSizeInPX(requireContext())
                )
                recyclerViewMovies.adapter = moviesAdapter
            }
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

    private fun updateMoviesList() {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {

            movies = moviesModel.getMovies()

            val diffResult =
                DiffUtil.calculateDiff(MoviesDiffUtilCallback(moviesAdapter.getData(), movies))
            moviesAdapter.setData(movies)

            withContext(Dispatchers.Main) {
                diffResult.dispatchUpdatesTo(moviesAdapter)
                recyclerViewMovies.scrollToPosition(0)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }


}