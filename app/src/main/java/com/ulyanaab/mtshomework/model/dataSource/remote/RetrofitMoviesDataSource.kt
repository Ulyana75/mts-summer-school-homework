package com.ulyanaab.mtshomework.model.dataSource.remote

import com.ulyanaab.mtshomework.App
import com.ulyanaab.mtshomework.model.dataSource.remote.retrofit.MovieResponse
import com.ulyanaab.mtshomework.model.dto.ActorDto
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilities.BASE_FOR_IMAGES
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.roundToInt

class RetrofitMoviesDataSource : MoviesDataSource {

    private var genresBaseList: List<GenreDto>? = null

    private var currentRequestedPage = 1


    override fun getMovies(): List<MovieDto> = runBlocking {
        val year = Calendar.getInstance().get(Calendar.YEAR) - 1
        val moviesResponse = App.moviesApi.getMovies( year, currentRequestedPage).results
        return@runBlocking convertToMoviesDto(moviesResponse)
    }

    override fun getNextPartMovies(): List<MovieDto> {
        currentRequestedPage += 1
        return getMovies()
    }

    override fun getPopularGenres(): List<GenreDto> {
        return listOf(
            GenreDto("боевики"), GenreDto("драмы"),
            GenreDto("комедии"), GenreDto("артхаус"),
            GenreDto("мелодрамы"), GenreDto("детективы")
        )
    }

    override fun getActors(movieId: Int): List<ActorDto> = runBlocking {
        val actors = App.moviesApi.getActors(movieId).cast
        actors.forEach {
            it.imageUrl = BASE_FOR_IMAGES + it.imageUrl
        }
        return@runBlocking actors
    }

    private suspend fun convertToMoviesDto(list: List<MovieResponse>): List<MovieDto> {
        val res = mutableListOf<Deferred<MovieDto>>()
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
                        getGenres(it.genre_ids),
                        it.id,
                        it.release_date
                    )
                }
            )
        }
        return res.map { it.await() }
    }

    private fun getRating(rating: Float): Int {
        return (rating / 2).roundToInt()
    }

    private suspend fun getAgeRestriction(id: Int): Int {
        val data = App.moviesApi.getReleaseDates(id).results
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
            genresBaseList = App.moviesApi.getGenresList().genres
        }
        return genresBaseList!!.filter { ids.contains(it.id) }
    }

}