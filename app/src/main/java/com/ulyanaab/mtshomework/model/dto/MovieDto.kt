package com.ulyanaab.mtshomework.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Movie")
data class MovieDto(
    val title: String,
    val description: String,
    val rateScore: Int,
    val ageRestriction: Int,
    val imageUrl: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
) : Serializable
