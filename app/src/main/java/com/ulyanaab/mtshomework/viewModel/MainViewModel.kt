package com.ulyanaab.mtshomework.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ulyanaab.mtshomework.model.dataSource.local.LocalMovieDataSource
import com.ulyanaab.mtshomework.model.dataSource.remote.MoviesDataSource
import com.ulyanaab.mtshomework.model.dataSource.remote.MoviesDataSourceWithDelay
import com.ulyanaab.mtshomework.model.dataSource.local.RoomLocalMovieDataSource
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilities.exceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var remoteModel: MoviesDataSource = MoviesDataSourceWithDelay()
    private var localModel: LocalMovieDataSource = RoomLocalMovieDataSource(application)

    val moviesLiveData: LiveData<List<MovieDto>> get() = _moviesLiveData
    private val _moviesLiveData = MutableLiveData<List<MovieDto>>()

    val genresLiveData: LiveData<List<GenreDto>> get() = _genresLiveData
    private val _genresLiveData = MutableLiveData<List<GenreDto>>()


    init {
        getPopularGenres()
        getMovies()
    }

    fun getPopularGenres() {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            _genresLiveData.postValue(remoteModel.getPopularGenres())
        }
    }

    private fun getMovies() {

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            _moviesLiveData.postValue(localModel.getAll())
        }

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val movies = remoteModel.getMovies()
            _moviesLiveData.postValue(movies)
            localModel.clear()
            localModel.addAll(movies)
        }
    }

    fun updateMovies(callback: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val movies = remoteModel.getMovies()
            _moviesLiveData.postValue(movies)

            withContext(Dispatchers.Main) {
                callback()
            }

            localModel.clear()
            localModel.addAll(movies)
        }
    }

}