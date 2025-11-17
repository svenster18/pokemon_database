package com.mohamadrizki.pokemondatabase.core.data.source.local

import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeUserHelper : UserDao {
    private var userData = mutableListOf<UserEntity>()

    override fun register(user: UserEntity) {
        userData.add(user)
    }

    override fun login(user: UserEntity): Flow<UserEntity> {
        return flowOf(userData.first { userEntity -> userEntity.username == user.username && userEntity.password == user.password })
    }
}