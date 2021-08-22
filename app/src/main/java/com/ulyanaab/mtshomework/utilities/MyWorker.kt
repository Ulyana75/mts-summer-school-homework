package com.ulyanaab.mtshomework.utilities

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ulyanaab.mtshomework.model.common.AppDatabase
import com.ulyanaab.mtshomework.model.dataSource.remote.MoviesDataSource
import com.ulyanaab.mtshomework.model.dataSource.remote.RetrofitMoviesDataSource

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    val dataSource: MoviesDataSource = RetrofitMoviesDataSource()
    val dao = AppDatabase.getInstance(context).movieDao()

    override fun doWork(): Result {
        return try {
            val movies = dataSource.getMovies()

            dao.clear()
            dao.addAll(movies)

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

}