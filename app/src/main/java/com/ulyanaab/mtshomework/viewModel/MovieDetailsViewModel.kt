package com.ulyanaab.mtshomework.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ulyanaab.mtshomework.model.dataSource.remote.MoviesDataSource
import com.ulyanaab.mtshomework.model.dataSource.remote.RetrofitMoviesDataSource
import com.ulyanaab.mtshomework.model.dto.ActorDto
import com.ulyanaab.mtshomework.utilities.exceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel : ViewModel() {

    private val dataSource: MoviesDataSource = RetrofitMoviesDataSource()

    val actorsLiveData: LiveData<List<ActorDto>> get() = _actorsLiveData
    private val _actorsLiveData = MutableLiveData<List<ActorDto>>()


    fun getActors(id: Int) {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            _actorsLiveData.postValue(
                dataSource.getActors(id)
            )
        }
    }

}