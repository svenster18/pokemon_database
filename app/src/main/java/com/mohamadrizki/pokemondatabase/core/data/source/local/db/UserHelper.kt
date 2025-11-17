package com.mohamadrizki.pokemondatabase.core.data.source.local.db

import android.content.Context
import com.couchbase.lite.Collection
import com.couchbase.lite.DataSource
import com.couchbase.lite.Database
import com.couchbase.lite.Expression
import com.couchbase.lite.MutableDocument
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.couchbase.lite.queryChangeFlow
import com.mohamadrizki.pokemondatabase.core.data.source.local.UserDao
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.NAME
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.PASSWORD
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.USERNAME
import com.mohamadrizki.pokemondatabase.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class UserHelper(context: Context): UserDao {
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: Database
    private lateinit var collection: Collection


    fun open() {
        database = databaseHelper.database
        collection = databaseHelper.collection
    }

    fun close() {
        database.close()
    }

    override fun register(user: UserEntity) {
        val mutableDocument = MutableDocument()
            .setString(NAME, user.name)
            .setString(USERNAME, user.username)
            .setString(PASSWORD, user.password)
        collection.save(mutableDocument)
    }

    override fun login(user: UserEntity): Flow<UserEntity> {
        val query = QueryBuilder.select(SelectResult.all())
            .from(DataSource.collection(collection))
            .where(Expression.property(USERNAME).equalTo(Expression.string(user.username)).and(
                Expression.property(PASSWORD).equalTo(Expression.string(user.password))
            ))
        return query.queryChangeFlow().mapNotNull { change ->
            change.error?.let { throw it }

            val firstResult = change.results?.firstOrNull()

            firstResult?.let { result ->
                val collectionDict = result.getDictionary(collection.name)
                collectionDict?.let { dict ->
                    UserEntity(
                        dict.getString(NAME) ?:"",
                        "",
                        ""
                    )
                }
            }
        }
    }

    companion object {
        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }
}