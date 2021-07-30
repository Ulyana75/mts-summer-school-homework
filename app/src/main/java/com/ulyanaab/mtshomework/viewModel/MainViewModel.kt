package com.ulyanaab.mtshomework.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ulyanaab.mtshomework.model.dataSource.MoviesDataSourceWithDelay
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.utilits.exceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private var model = MoviesDataSourceWithDelay()

    val moviesLiveData: LiveData<List<MovieDto>> get() = _moviesLiveData
    private val _moviesLiveData = MutableLiveData<List<MovieDto>>()

    val genresLiveData: LiveData<List<GenreDto>> get() = _genresLiveData
    private val _genresLiveData = MutableLiveData<List<GenreDto>>()

    var cacheMovieData = listOf<MovieDto>()

    init {
        getPopularGenres()
        getMovies()
    }

    fun getPopularGenres() {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            _genresLiveData.postValue(model.getPopularGenres())
        }
    }

    fun getMovies(callback: () -> Unit = {}) {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val movies = model.getMovies()
            _moviesLiveData.postValue(movies)
            cacheMovieData = movies

            withContext(Dispatchers.Main) {
                callback()
            }
        }
    }

}