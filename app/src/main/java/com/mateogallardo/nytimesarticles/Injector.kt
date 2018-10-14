package com.mateogallardo.nytimesarticles

import android.arch.persistence.room.Room
import android.content.Context
import com.mateogallardo.nytimesarticles.data.api.HttpService
import com.mateogallardo.nytimesarticles.data.api.RetrofitHttpService
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.database.RoomDatabaseImpl

class Injector {
    companion object {
        fun getDatabaseImplementation(context: Context? = null, testMode: Boolean = false): Database {
            return if (testMode) {
                Room.inMemoryDatabaseBuilder(context!!, RoomDatabaseImpl::class.java).build()
            } else {
                RoomDatabaseImpl.getInstance(context!!)!!
            }
        }

        fun getHttpService(context: Context? = null, testMode: Boolean = false): HttpService {
            return if (testMode) {
                RetrofitHttpService() //TODO: Mock this
            } else {
                RetrofitHttpService()
            }
        }
    }
}