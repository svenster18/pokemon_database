package com.mohamadrizki.pokemondatabase.utils

import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import com.mohamadrizki.pokemondatabase.core.domain.model.User

object DataDummy {
    fun generateDummyUserEntity(): UserEntity = UserEntity(
        name = "Rizki",
        username = "rizki",
        password = "rizki"
    )

    fun generateDummyUser(): User = User(
        name = "Rizki",
        username = "rizki",
        password = "rizki"
    )
}