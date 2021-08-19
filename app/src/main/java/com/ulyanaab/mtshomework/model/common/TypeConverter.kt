package com.ulyanaab.mtshomework.model.common

import androidx.room.TypeConverter
import com.ulyanaab.mtshomework.model.dto.GenreDto

class TypeConverter {

    @TypeConverter
    fun fromGenres(list: List<GenreDto>): String {
        return list.joinToString(separator = ",") { it.name }
    }

    @TypeConverter
    fun toGenre(str: String): List<GenreDto> {
        val list = mutableListOf<GenreDto>()
        str.split(",").forEach {
            list.add(GenreDto(it))
        }
        return list
    }
}