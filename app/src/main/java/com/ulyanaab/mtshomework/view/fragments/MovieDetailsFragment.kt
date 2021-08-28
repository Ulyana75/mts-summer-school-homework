package com.ulyanaab.mtshomework.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilities.KEY_TO_SEND_MOVIEDTO
import com.ulyanaab.mtshomework.utilities.LoadingStates
import com.ulyanaab.mtshomework.utilities.loadImageAsync
import com.ulyanaab.mtshomework.utilities.setRating
import com.ulyanaab.mtshomework.view.recyclerView.ActorsAdapter
import com.ulyanaab.mtshomework.view.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.viewModel.MovieDetailsViewModel


class MovieDetailsFragment : Fragment() {

    private val actorViewModel: MovieDetailsViewModel by viewModels()

    private var movieObj: MovieDto? = null

    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewGenres: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postponeEnterTransition()
        movieObj = arguments?.getSerializable(KEY_TO_SEND_MOVIEDTO) as MovieDto?
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.movie_image).transitionName = "poster_${movieObj?.title}"
    }

    override fun onStart() {
        super.onStart()
        if (movieObj != null) {
            initViews()
            getActors()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        with(requireView()) {
            findViewById<ImageView>(R.id.movie_image).loadImageAsync(movieObj!!.imageUrl) {
                startPostponedEnterTransition()
            }
            findViewById<TextView>(R.id.movie_title).text = movieObj!!.title
            findViewById<TextView>(R.id.age_restriction).text = "${movieObj!!.ageRestriction}+"
            setRating(movieObj!!.rateScore, this)
            findViewById<TextView>(R.id.description).text = movieObj!!.description
            findViewById<TextView>(R.id.date).text = movieObj!!.date
        }

        recyclerViewActors = requireView().findViewById(R.id.recycler_view_actors)
        val adapter = ActorsAdapter(listOf())
        recyclerViewActors.adapter = adapter

        recyclerViewGenres = requireView().findViewById(R.id.recycler_view_genre)
        val adapter2 = GenreAdapter(movieObj!!.genres, {})
        recyclerViewGenres.adapter = adapter2

        actorViewModel.actorsLiveData.observe(this) {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        }

        actorViewModel.statesLiveData.observe(this) {
            requireView().findViewById<View>(R.id.progress_bar).visibility = when (it) {
                LoadingStates.LOADING -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun getActors() {
        actorViewModel.getActors(movieObj!!.id)
    }

}