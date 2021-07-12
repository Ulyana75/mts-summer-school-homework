package com.ulyanaab.mtshomework

import com.ulyanaab.mtshomework.movies.MoviesDataSource


class MoviesModel(
	private val moviesDataSource: MoviesDataSource
) {
	
	fun getMovies() = moviesDataSource.getMovies()
}