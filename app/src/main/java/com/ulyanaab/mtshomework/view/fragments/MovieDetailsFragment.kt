package com.ulyanaab.mtshomework.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.ActorDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilities.KEY_TO_SEND_MOVIEDTO
import com.ulyanaab.mtshomework.utilities.loadImageAsync
import com.ulyanaab.mtshomework.view.recyclerView.ActorsAdapter
import com.ulyanaab.mtshomework.utilities.setRating
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
        movieObj = arguments?.getSerializable(KEY_TO_SEND_MOVIEDTO) as MovieDto?
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
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
            findViewById<ImageView>(R.id.movie_image).loadImageAsync(movieObj!!.backgroundPosterUrl)
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
    }

    private fun getActors() {
        actorViewModel.getActors(movieObj!!.id)
    }

}