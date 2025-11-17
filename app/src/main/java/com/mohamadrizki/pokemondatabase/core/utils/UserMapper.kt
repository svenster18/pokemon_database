package com.mohamadrizki.pokemondatabase.core.utils

import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import com.mohamadrizki.pokemondatabase.core.domain.model.User

object UserMapper {

    fun mapEntityToDomain(input: UserEntity): User = User(
        name = input.name,
        username = input.username,
        password = input.password,
    )

    fun mapDomainToEntity(input: User): UserEntity = UserEntity(
        name = input.name,
        username = input.username,
        password = input.password,
    )
}