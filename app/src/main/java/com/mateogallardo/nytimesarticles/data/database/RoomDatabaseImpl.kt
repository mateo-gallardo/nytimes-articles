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
        instance?.close()
        instance = null
    }

    companion object {
        @Volatile
        private var instance: RoomDatabaseImpl? = null

        fun getInstance(context: Context): RoomDatabaseImpl? =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext,
                        RoomDatabaseImpl::class.java, "weather.db")
                        .build().also { instance = it }
            }
    }
}