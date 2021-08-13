package com.ulyanaab.mtshomework.model.daos

import androidx.room.*
import com.ulyanaab.mtshomework.model.common.TypeConverter
import com.ulyanaab.mtshomework.model.dto.UserDto

@Dao
@TypeConverters(TypeConverter::class)
interface UserDao {

    @Query("SELECT * FROM User WHERE id LIKE :id")
    fun getUserById(id: Long): UserDto

    @Query("SELECT * FROM User")
    fun getAll(): List<UserDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: UserDto): Long

    @Update
    fun updateUser(user: UserDto)

    @Delete
    fun deleteUser(user: UserDto)

}