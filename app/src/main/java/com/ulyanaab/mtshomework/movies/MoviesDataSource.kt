package com.ulyanaab.mtshomework.movies

import com.ulyanaab.mtshomework.dto.MovieDto

interface MoviesDataSource {
	fun getMovies(): List<MovieDto>
}