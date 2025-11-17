package com.mohamadrizki.pokemondatabase.core.data.source.local

import com.mohamadrizki.pokemondatabase.core.data.source.local.db.UserHelper
import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val userHelper: UserDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(userHelper: UserDao): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(userHelper)
            }
    }

    suspend fun register(user: UserEntity) = userHelper.register(user)

    fun login(user: UserEntity): Flow<UserEntity?> = userHelper.login(user)
}