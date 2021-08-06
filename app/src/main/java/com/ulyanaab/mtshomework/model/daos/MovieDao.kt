package com.ulyanaab.mtshomework.model.daos

import androidx.room.*
import com.ulyanaab.mtshomework.model.dto.MovieDto

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: MovieDto)

    @Update
    fun update(movie: MovieDto)

    @Delete
    fun delete(movie: MovieDto)

    @Query("SELECT * FROM Movie WHERE title LIKE :title")
    fun getMovieByTitle(title: String): MovieDto

}