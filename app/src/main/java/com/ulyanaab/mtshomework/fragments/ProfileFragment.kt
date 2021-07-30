package com.ulyanaab.mtshomework.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.recyclerView.GenreAdapter


class ProfileFragment : Fragment() {

    private lateinit var recyclerViewInterests: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        recyclerViewInterests = requireView().findViewById(R.id.recycler_view_interests)
        val adapter = GenreAdapter(getGenres(), this::adapterGenreListener)
        recyclerViewInterests.adapter = adapter
    }

    private fun getGenres(): List<GenreDto> {
        return listOf(GenreDto("боевики"), GenreDto("драмы"), GenreDto("комедии"))
    }

    private fun adapterGenreListener(item: GenreDto) {
        // do nothing
    }

}