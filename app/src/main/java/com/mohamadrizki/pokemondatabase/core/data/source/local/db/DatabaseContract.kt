package com.mohamadrizki.pokemondatabase.core.data.source.local.db

internal class DatabaseContract {
    internal class UserColumns {
        companion object {
            const val COLLECTION_NAME = "user"
            const val NAME = "name"
            const val USERNAME = "username"
            const val PASSWORD = "password"
        }
    }
}