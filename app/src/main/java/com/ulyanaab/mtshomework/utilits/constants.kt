package com.ulyanaab.mtshomework.utilits

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

const val coroutineExceptionTag = "from coroutine"

val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    Log.d(coroutineExceptionTag, "$exception")
}

const val DISTANCE_FOR_SWIPE_REFRESH = 500