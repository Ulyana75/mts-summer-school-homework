package com.ulyanaab.mtshomework.model.daos

import androidx.room.*
import com.ulyanaab.mtshomework.model.dto.MovieDto

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: MovieDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(movies: List<MovieDto>)

    @Update
    fun update(movie: MovieDto)

    @Delete
    fun delete(movie: MovieDto)

    @Query("DELETE FROM Movie")
    fun clear()

}