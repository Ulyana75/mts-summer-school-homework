package com.ulyanaab.mtshomework.model.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ulyanaab.mtshomework.model.daos.MovieDao
import com.ulyanaab.mtshomework.model.daos.UserDao
import com.ulyanaab.mtshomework.model.dto.MovieDto
import com.ulyanaab.mtshomework.model.dto.UserDto
import com.ulyanaab.mtshomework.utilities.DATABASE_NAME

@Database(entities = [UserDto::class, MovieDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                instance = _instance
                _instance
            }
        }
    }

}