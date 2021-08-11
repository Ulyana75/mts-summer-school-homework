package com.ulyanaab.mtshomework.utilities

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

const val coroutineExceptionTag = "from coroutine"

val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    Log.d(coroutineExceptionTag, "$exception")
}

const val DISTANCE_FOR_SWIPE_REFRESH = 500

const val KEY_TO_SEND_MOVIEDTO = "movie"

const val DATABASE_NAME = "movies_database"

const val KEY_USER_ID = "userId"

const val KEY_CURRENT_USER = "currentUser"