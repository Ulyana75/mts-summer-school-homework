package com.ulyanaab.mtshomework.model.dataSource.remote.retrofit

import com.ulyanaab.mtshomework.model.dto.GenreDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie?language=ru&sort_by=popularity.desc")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") sinceYear: Int
    ): RetrofitResponse

    @GET("movie/{movie_id}/release_dates")
    suspend fun getReleaseDates(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): ResponseForAgeRestriction

    @GET("genre/movie/list?language=ru")
    suspend fun getGenresList(
        @Query("api_key") apiKey: String
    ): GenresResponse

}