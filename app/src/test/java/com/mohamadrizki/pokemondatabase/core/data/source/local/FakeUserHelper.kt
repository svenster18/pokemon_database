package com.mohamadrizki.pokemondatabase.core.data.source.local

import com.couchbase.lite.DataSource
import com.couchbase.lite.Expression
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.couchbase.lite.queryChangeFlow
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.NAME
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.PASSWORD
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull

class FakeUserHelper: UserDao {
    private var userData = mutableListOf<UserEntity>()

    override suspend fun register(user: UserEntity) {
        userData.add(user)
    }

    override fun login(user: UserEntity): Flow<UserEntity> {
        return flowOf(userData.first { userEntity ->  userEntity.username == user.username && userEntity.password == user.password })
    }
}