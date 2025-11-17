package com.mohamadrizki.pokemondatabase.core.data.source.local

import com.mohamadrizki.pokemondatabase.core.data.source.local.db.UserHelper
import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val userHelper: UserHelper) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(userHelper: UserHelper): LocalDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocalDataSource(userHelper)
            }
    }

    suspend fun createUser(user: UserEntity) = userHelper.register(user)

    fun login(user: UserEntity): Flow<UserEntity?> = userHelper.login(user)
}