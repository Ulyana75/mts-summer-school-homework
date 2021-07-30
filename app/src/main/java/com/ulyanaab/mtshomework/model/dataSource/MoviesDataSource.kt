package com.ulyanaab.mtshomework.model.dataSource

import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto

interface MoviesDataSource {
    fun getMovies(): List<MovieDto>

    fun getPopularGenres(): List<GenreDto>
}