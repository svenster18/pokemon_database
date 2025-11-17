package com.mohamadrizki.pokemondatabase.core.data.source.local.db

import android.content.Context
import android.util.Log
import com.couchbase.lite.Collection
import com.couchbase.lite.CouchbaseLite
import com.couchbase.lite.Database
import com.mohamadrizki.pokemondatabase.core.data.source.local.db.DatabaseContract.UserColumns.Companion.COLLECTION_NAME

internal class DatabaseHelper(context: Context) {
    var database: Database
    var collection: Collection

    // Initialize Couchbase Lite

    init {
        init(context)
        database = createDb(DATABASE_NAME)
        collection = createCollection(COLLECTION_NAME)
    }

    private fun init(context: Context) {
        CouchbaseLite.init(context)
        Log.i(TAG, "CBL Initialized")
    }


    // Create a database
    fun createDb(dbName: String): Database {
        Log.i(TAG, "Database created: $dbName")
        return Database(dbName)
    }

    fun createCollection(collName: String): Collection {
        Log.i(TAG, "Collection created: $collection")
        return database.createCollection(collName)
    }

    companion object {
        private const val TAG = "DatabaseHelper"
        const val DATABASE_NAME = "dbpokemonapp"
    }
}