package com.mateogallardo.nytimesarticles

import android.arch.persistence.room.Room
import android.content.Context
import com.mateogallardo.nytimesarticles.data.api.HttpService
import com.mateogallardo.nytimesarticles.data.api.stub.HttpServicesStub
import com.mateogallardo.nytimesarticles.data.api.retrofit.RetrofitHttpService
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.database.RoomDatabaseImpl
import com.mateogallardo.nytimesarticles.data.database.RoomDatabaseMock

class Injector {
    companion object {
        fun getDatabaseImplementation(context: Context? = null, testMode: Boolean = false): Database {
            return if (testMode) {
                RoomDatabaseMock.getInstance(context!!)!!
            } else {
                RoomDatabaseImpl.getInstance(context!!)!!
            }
        }

        fun getHttpService(context: Context? = null, testMode: Boolean = false): HttpService {
            return if (testMode) {
                HttpServicesStub(context!!)
            } else {
                RetrofitHttpService()
            }
        }
    }
}