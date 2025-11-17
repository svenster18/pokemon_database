package com.mohamadrizki.pokemondatabase.core.data.source.local

import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDao {

    suspend fun register(user: UserEntity)
    fun login(user: UserEntity): Flow<UserEntity>
}