package com.ulyanaab.mtshomework.model.dataSource.remote

import com.ulyanaab.mtshomework.model.dto.ActorDto
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto

interface MoviesDataSource {

    fun getMovies(): List<MovieDto>
    fun getNextPartMovies(): List<MovieDto>
    fun getPopularGenres(): List<GenreDto>
    fun getActors(movieId: Int): List<ActorDto>

}