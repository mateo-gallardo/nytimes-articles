package com.mateogallardo.nytimesarticles

import android.content.Context
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.database.DummyDatabase
import com.mateogallardo.nytimesarticles.data.database.RoomDatabaseImpl

class Injector {
    companion object {
        fun getDatabaseImplementation(context: Context? = null, testMode: Boolean = false): Database {
            return if (testMode) {
                DummyDatabase()
            } else {
                RoomDatabaseImpl.getInstance(context!!)!!
            }
        }
    }
}