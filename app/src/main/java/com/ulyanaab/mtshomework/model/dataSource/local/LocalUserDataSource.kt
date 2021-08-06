package com.ulyanaab.mtshomework.model.dataSource.local

import com.ulyanaab.mtshomework.model.dto.UserDto

interface LocalUserDataSource {

    fun getById(id: Long): UserDto

    fun getAll(): List<UserDto>

    fun add(user: UserDto): Long

    fun update(user: UserDto)

    fun delete(user: UserDto)

}