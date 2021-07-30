package com.ulyanaab.mtshomework.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.ActorDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.recyclerView.ActorsAdapter
import com.ulyanaab.mtshomework.utilits.setRating


class MovieDetailsFragment : Fragment() {

    private var movieObj: MovieDto? = null

    private lateinit var recyclerViewActors: RecyclerView

    companion object {

        private val KEY_TO_SEND_MOVIEDTO = "movie"

        fun newInstance(message: MovieDto): Fragment {
            val args = Bundle()
            args.putSerializable(KEY_TO_SEND_MOVIEDTO, message)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

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
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        with(requireView()) {
            findViewById<ImageView>(R.id.movie_image).load(movieObj!!.imageUrl)
            findViewById<TextView>(R.id.movie_title).text = movieObj!!.title
            findViewById<TextView>(R.id.age_restriction).text = "${movieObj!!.ageRestriction}+"
            setRating(movieObj!!.rateScore, this)
            findViewById<TextView>(R.id.description).text = movieObj!!.description
        }

        recyclerViewActors = requireView().findViewById(R.id.recycler_view_actors)
        val adapter = ActorsAdapter(getActors())
        recyclerViewActors.adapter = adapter
    }

    private fun getActors(): MutableList<ActorDto> {
        return mutableListOf(
            ActorDto("Джейсон Стэйтем", R.drawable.img_actor_stethem),
            ActorDto("Холт Маккэллани", R.drawable.img_actor_mackellany),
            ActorDto("Джош Харнетт", R.drawable.img_actor_harnett)
        )
    }

}