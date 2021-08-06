package com.ulyanaab.mtshomework.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ulyanaab.mtshomework.model.common.TypeConverter

@Entity(tableName = "User")
data class UserDto(
    var name: String = "",
    var password: String = "",
    var email: String = "",
    var phone: String = "",
    var photoUrl: String = "",

    @field:TypeConverters(TypeConverter::class)
    var genres: List<GenreDto> = listOf(
        GenreDto("боевики"),
        GenreDto("драмы"),
        GenreDto("комедии")
    ),

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)