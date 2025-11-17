package com.mohamadrizki.pokemondatabase.core.data

import com.mohamadrizki.pokemondatabase.core.data.source.local.LocalDataSource
import com.mohamadrizki.pokemondatabase.core.domain.model.User
import com.mohamadrizki.pokemondatabase.core.utils.AppExecutors
import com.mohamadrizki.pokemondatabase.core.utils.UserMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository private constructor(
    private val localDataSource: LocalDataSource,
){

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            localData: LocalDataSource,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository( localData)
            }
    }
    suspend fun register(user: User) {
        val userEntity = UserMapper.mapDomainToEntity(user)
        localDataSource.register(userEntity)
    }

    fun login(user: User): Flow<User?> {
        val userEntity = UserMapper.mapDomainToEntity(user)
        return localDataSource.login(userEntity).map {
            if (it != null) UserMapper.mapEntityToDomain(it) else null
        }
    }
}