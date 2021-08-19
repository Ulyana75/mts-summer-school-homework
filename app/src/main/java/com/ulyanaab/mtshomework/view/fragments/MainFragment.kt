package com.ulyanaab.mtshomework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilities.DISTANCE_FOR_SWIPE_REFRESH
import com.ulyanaab.mtshomework.utilities.KEY_TO_SEND_MOVIEDTO
import com.ulyanaab.mtshomework.utilities.LoadingStates
import com.ulyanaab.mtshomework.utilities.calculateImageSizeInPX
import com.ulyanaab.mtshomework.view.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.view.recyclerView.MoviesAdapter
import com.ulyanaab.mtshomework.view.recyclerView.diffUtil.MoviesDiffUtilCallback
import com.ulyanaab.mtshomework.viewModel.MainViewModel
import kotlinx.coroutines.*


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesLayoutManager: GridLayoutManager
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var lastPosition = 0


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

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<View>(R.id.active_home).visibility = View.VISIBLE
        requireActivity().findViewById<View>(R.id.active_profile).visibility = View.INVISIBLE

        recyclerViewMovies.scrollToPosition(lastPosition)
    }

    private fun initViews() {
        initRecyclerViews()
        initSwipeRefresh()

        viewModel.statesLiveData.observe(this) {
            requireView().findViewById<View>(R.id.progress_bar).visibility = when (it) {
                LoadingStates.LOADING -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun initSwipeRefresh() {
        swipeRefreshLayout = requireView().findViewById(R.id.swipe_refresh)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateMovies(callback = {
                recyclerViewMovies.scrollToPosition(0)
                swipeRefreshLayout.isRefreshing = false
            })
            viewModel.getPopularGenres()
        }

        swipeRefreshLayout.setDistanceToTriggerSync(DISTANCE_FOR_SWIPE_REFRESH)
    }

    private fun initRecyclerViews() {
        initGenresRecyclerView()
        initMoviesRecyclerView()
    }

    private fun initMoviesRecyclerView() {
        recyclerViewMovies = requireView().findViewById(R.id.recycler_view_movies)
        moviesAdapter = MoviesAdapter(
            listOf(),
            this@MainFragment::adapterMovieListener,
            calculateImageSizeInPX(requireContext())
        )
        moviesLayoutManager = GridLayoutManager(requireContext(), 2)

        recyclerViewMovies.adapter = moviesAdapter
        recyclerViewMovies.layoutManager = moviesLayoutManager

        recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastPosition = moviesLayoutManager.findFirstVisibleItemPosition()
                if (dy > 0 && moviesLayoutManager.findFirstVisibleItemPosition() >= moviesAdapter.itemCount - 5) {
                    viewModel.getNextPartMovies()
                }
            }

        })

        viewModel.moviesLiveData.observe(this, {
            val diffResult =
                DiffUtil.calculateDiff(MoviesDiffUtilCallback(moviesAdapter.getData(), it))
            moviesAdapter.setData(it)

            diffResult.dispatchUpdatesTo(moviesAdapter)
        })
    }

    private fun initGenresRecyclerView() {
        recyclerViewGenres = requireView().findViewById(R.id.recycler_view_genre)
        val adapter = GenreAdapter(listOf(), this::adapterGenreListener)
        recyclerViewGenres.adapter = adapter

        viewModel.genresLiveData.observe(this, {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun adapterMovieListener(item: MovieDto) {
        requireView().findNavController().navigate(
            R.id.movieDetailsFragment, bundleOf(
                KEY_TO_SEND_MOVIEDTO to item
            )
        )
    }

    private fun adapterGenreListener(item: GenreDto) {
        showToast(item.name)
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