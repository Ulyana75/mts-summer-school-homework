package com.ulyanaab.mtshomework.model.dataSource.remote

import com.ulyanaab.mtshomework.App
import com.ulyanaab.mtshomework.model.dataSource.remote.retrofit.MovieResponse
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilities.API_KEY
import com.ulyanaab.mtshomework.utilities.BASE_FOR_IMAGES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class RetrofitMoviesDataSource : MoviesDataSource {

    private var genresBaseList: List<GenreDto>? = null


    override fun getMovies(): List<MovieDto> = runBlocking {
        val moviesResponse = App.moviesApi.getMovies(API_KEY, 2021).results // TODO current year
        return@runBlocking convertToMoviesDto(moviesResponse)
    }

    override fun getPopularGenres(): List<GenreDto> {
        return listOf(
            GenreDto("боевики"), GenreDto("драмы"),
            GenreDto("комедии"), GenreDto("артхаус"),
            GenreDto("мелодрамы"), GenreDto("детективы")
        )
    }

    private suspend fun convertToMoviesDto(list: List<MovieResponse>): List<MovieDto> {
        val res = mutableListOf<MovieDto>()
        list.forEach {
            res.add(
                CoroutineScope(Dispatchers.IO).async {
                    MovieDto(
                        it.title,
                        it.description,
                        getRating(it.rating),
                        getAgeRestriction(it.id),
                        BASE_FOR_IMAGES + it.poster_path,
                        BASE_FOR_IMAGES + it.backdrop_path,
                        getGenres(it.genre_ids)
                    )
                }.await()
            )
        }
        return res
    }

    private fun getRating(rating: Float): Int {
        return (rating / 2).roundToInt()
    }

    private suspend fun getAgeRestriction(id: Int): Int {
        val data = App.moviesApi.getReleaseDates(id, API_KEY).results
        data.forEach {
            if (it.iso == "RU") {
                val ageRestr = it.release_dates[0].certification
                if(ageRestr != "") {
                    return ageRestr.substring(0, ageRestr.length - 1).toInt()
                }
            }
        }
        return -1
    }

    private suspend fun getGenres(ids: List<Int>): List<GenreDto> {
        if (genresBaseList == null) {
            genresBaseList = App.moviesApi.getGenresList(API_KEY).genres
        }
        return genresBaseList!!.filter { ids.contains(it.id) }
    }

}