package com.mohamadrizki.pokemondatabase.utils

import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity

object DataDummy {
    fun generateDummyUserEntity(): UserEntity = UserEntity(
        name = "Rizki",
        username = "rizki",
        password = "rizki"
    )
}