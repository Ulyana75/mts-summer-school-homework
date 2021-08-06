package com.ulyanaab.mtshomework.model.dataSource.local

import com.ulyanaab.mtshomework.model.dto.MovieDto

interface LocalMovieDataSource {

    fun getAll(): List<MovieDto>

    fun add(movie: MovieDto)

    fun addAll(movies: List<MovieDto>)

    fun update(movie: MovieDto)

    fun delete(movie: MovieDto)

}