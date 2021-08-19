package com.ulyanaab.mtshomework.model.dataSource.remote.retrofit

import com.google.gson.annotations.SerializedName
import com.ulyanaab.mtshomework.model.dto.GenreDto

data class RetrofitResponse(
    val results: List<MovieResponse>
)


data class MovieResponse(
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val description: String,
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val rating: Float
)


data class ResponseForAgeRestriction(
    val results: List<ResponseAgeRestrictionData>
)


data class ResponseAgeRestrictionData(
    @SerializedName("iso_3166_1")
    val iso: String,
    @SerializedName("release_dates")
    val release_dates: List<ReleaseDateObj>
)


data class ReleaseDateObj(
    @SerializedName("certification")
    val certification: String
)


data class GenresResponse(
    val genres: List<GenreDto>
)