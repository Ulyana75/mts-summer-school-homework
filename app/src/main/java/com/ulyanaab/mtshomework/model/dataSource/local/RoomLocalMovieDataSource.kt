package com.ulyanaab.mtshomework.model.dataSource.local

import android.content.Context
import com.ulyanaab.mtshomework.model.common.AppDatabase
import com.ulyanaab.mtshomework.model.dto.MovieDto

class RoomLocalMovieDataSource(context: Context) : LocalMovieDataSource {

    private val movieDao = AppDatabase.getInstance(context).movieDao()

    override fun getAll(): List<MovieDto> {
        return movieDao.getAll()
    }

    override fun add(movie: MovieDto) {
        movieDao.add(movie)
    }

    override fun addAll(movies: List<MovieDto>) {
        movieDao.addAll(movies)
    }

    override fun update(movie: MovieDto) {
        movieDao.update(movie)
    }

    override fun delete(movie: MovieDto) {
        movieDao.delete(movie)
    }

    override fun clear() {
        movieDao.clear()
    }
}