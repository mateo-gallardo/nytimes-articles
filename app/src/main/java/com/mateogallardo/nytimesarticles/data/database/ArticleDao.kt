package com.mateogallardo.nytimesarticles.data.database

import android.arch.lifecycle.LiveData
import com.mateogallardo.nytimesarticles.data.model.Article

interface ArticleDao {
    fun addArticle(article: Article)
    fun getArticles(): LiveData<List<Article>>
}