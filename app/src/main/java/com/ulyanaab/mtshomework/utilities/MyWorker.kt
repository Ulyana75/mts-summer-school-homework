package com.ulyanaab.mtshomework.utilities

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ulyanaab.mtshomework.model.common.AppDatabase
import com.ulyanaab.mtshomework.model.dataSource.local.LocalMovieDataSource
import com.ulyanaab.mtshomework.model.dataSource.local.RoomLocalMovieDataSource
import com.ulyanaab.mtshomework.model.dataSource.remote.MoviesDataSource
import com.ulyanaab.mtshomework.model.dataSource.remote.RetrofitMoviesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val dataSource: MoviesDataSource = RetrofitMoviesDataSource()
    private val localDataSource: LocalMovieDataSource = RoomLocalMovieDataSource(context)

    override fun doWork(): Result {
        return try {
            val movies = dataSource.getMovies()

            localDataSource.clear()
            localDataSource.addAll(movies)

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

}