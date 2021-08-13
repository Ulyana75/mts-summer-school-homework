package com.ulyanaab.mtshomework.model.dataSource.local

import android.content.Context
import com.ulyanaab.mtshomework.model.common.AppDatabase
import com.ulyanaab.mtshomework.model.dto.UserDto

class RoomLocalUserDataSource(context: Context) : LocalUserDataSource {

    private val userDao = AppDatabase.getInstance(context).userDao()

    override fun getById(id: Long): UserDto {
        return userDao.getUserById(id)
    }

    override fun getAll(): List<UserDto> {
        return userDao.getAll()
    }

    override fun add(user: UserDto): Long {
        return userDao.addUser(user)
    }

    override fun update(user: UserDto) {
        userDao.updateUser(user)
    }

    override fun delete(user: UserDto) {
        userDao.deleteUser(user)
    }
}