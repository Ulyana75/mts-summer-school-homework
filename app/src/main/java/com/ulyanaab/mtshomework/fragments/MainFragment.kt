package com.ulyanaab.mtshomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.utilits.calculateImageSizeInPX
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.recyclerView.MoviesAdapter
import com.ulyanaab.mtshomework.recyclerView.diffUtil.MoviesDiffUtilCallback
import com.ulyanaab.mtshomework.utilits.DISTANCE_FOR_SWIPE_REFRESH
import com.ulyanaab.mtshomework.utilits.replaceFragment
import com.ulyanaab.mtshomework.viewModel.MainViewModel
import kotlinx.coroutines.*


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this.requireActivity()).get(MainViewModel::class.java)
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
            viewModel.getMovies()
            viewModel.getPopularGenres()
        }

        swipeRefreshLayout.setDistanceToTriggerSync(DISTANCE_FOR_SWIPE_REFRESH)
    }

    private fun initRecyclerViews() {
        recyclerViewGenres = requireView().findViewById(R.id.recycler_view_genre)
        val adapter = GenreAdapter(listOf(), this::adapterGenreListener)
        recyclerViewGenres.adapter = adapter

        viewModel.genresLiveData.observe(this, {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })


        recyclerViewMovies = requireView().findViewById(R.id.recycler_view_movies)
        moviesAdapter = MoviesAdapter(
            viewModel.cacheMovieData,
            this@MainFragment::adapterMovieListener,
            calculateImageSizeInPX(requireContext())
        )
        recyclerViewMovies.adapter = moviesAdapter

        viewModel.moviesLiveData.observe(this, {
            val diffResult =
                DiffUtil.calculateDiff(MoviesDiffUtilCallback(moviesAdapter.getData(), it))
            moviesAdapter.setData(it)

            diffResult.dispatchUpdatesTo(moviesAdapter)
            recyclerViewMovies.scrollToPosition(0)
            swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun adapterMovieListener(item: MovieDto) {
        replaceFragment(requireActivity(), MovieDetailsFragment.newInstance(item), true)
    }

    private fun adapterGenreListener(item: GenreDto) {
        showToast(item.genre)
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