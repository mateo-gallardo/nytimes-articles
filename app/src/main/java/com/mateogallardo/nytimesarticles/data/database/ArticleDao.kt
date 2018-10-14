package com.mateogallardo.nytimesarticles.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mateogallardo.nytimesarticles.data.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticle(article: Article)

    @Query("SELECT * FROM Article")
    fun getArticles(): List<Article>
}