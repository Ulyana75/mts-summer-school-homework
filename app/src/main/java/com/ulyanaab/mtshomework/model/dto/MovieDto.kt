package com.ulyanaab.mtshomework.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ulyanaab.mtshomework.model.common.TypeConverter
import java.io.Serializable

@Entity(tableName = "Movie")
data class MovieDto(
    @PrimaryKey
    val title: String,
    val description: String,
    val rateScore: Int,
    val ageRestriction: Int,
    val imageUrl: String,
    val backgroundPosterUrl: String="",
    @field:TypeConverters(TypeConverter::class)
    val genres: List<GenreDto> = listOf()
) : Serializable
