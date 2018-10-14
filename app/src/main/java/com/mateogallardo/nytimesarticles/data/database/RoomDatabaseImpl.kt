package com.mateogallardo.nytimesarticles.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mateogallardo.nytimesarticles.data.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class RoomDatabaseImpl : RoomDatabase(), com.mateogallardo.nytimesarticles.data.database.Database {
    abstract override fun getArticleDao(): ArticleDao

    override fun closeConnection() {
        INSTANCE?.close()
    }

    companion object {
        private var INSTANCE: RoomDatabaseImpl? = null

        fun getInstance(context: Context): RoomDatabaseImpl? {
            if (INSTANCE == null) {
                synchronized(RoomDatabaseImpl::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            RoomDatabaseImpl::class.java, "weather.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}