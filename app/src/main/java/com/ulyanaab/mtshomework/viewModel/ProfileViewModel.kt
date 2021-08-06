package com.ulyanaab.mtshomework.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ulyanaab.mtshomework.model.daos.UserDao
import com.ulyanaab.mtshomework.model.dto.UserDto
import com.ulyanaab.mtshomework.model.common.AppDatabase
import com.ulyanaab.mtshomework.utilities.exceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    val profileLiveData: LiveData<UserDto> get() = _profileLiveData
    private val _profileLiveData = MutableLiveData<UserDto>()

    private val userDao: UserDao = AppDatabase.getInstance(application).userDao()


    init {
        initCurrentUser()
    }

    private fun initCurrentUser() {
        val sPref = getApplication<Application>().getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        val userId = sPref.getLong("userId", -1)

        if(userId == -1L) {

            CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
                val newId = userDao.addUser(UserDto())
                sPref.edit().putLong("userId", newId).apply()
                postValueById(newId)
            }

        } else {
            postValueById(userId)
        }
    }

    private fun postValueById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val user = userDao.getUserById(id)
            _profileLiveData.postValue(user)
        }
    }

    fun updateUserData(user: UserDto) {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            userDao.updateUser(user)
            _profileLiveData.postValue(user)
        }
    }

}