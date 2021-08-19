package com.ulyanaab.mtshomework.model.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int = 0
)