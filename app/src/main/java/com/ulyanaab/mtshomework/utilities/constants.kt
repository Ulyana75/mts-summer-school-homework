package com.ulyanaab.mtshomework.utilities

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

const val coroutineExceptionTag = "from coroutine"

val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    Log.d(coroutineExceptionTag, "${exception.printStackTrace()}")
}

const val DISTANCE_FOR_SWIPE_REFRESH = 500

const val KEY_TO_SEND_MOVIEDTO = "movie"

const val DATABASE_NAME = "movies_database"

const val KEY_USER_ID = "userId"

const val KEY_CURRENT_USER = "currentUser"

const val BASE_URL = "https://api.themoviedb.org/3/"

const val BASE_FOR_IMAGES = "https://image.tmdb.org/t/p/w500"

const val API_KEY = "a6cc6d9478aa7991d721ec2c20c58fd2"