package com.ulyanaab.mtshomework.model.dto

import com.google.gson.annotations.SerializedName

data class ActorDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("profile_path")
    var imageUrl: String
)
