package com.mateogallardo.nytimesarticles.data.repository

import android.arch.lifecycle.LiveData
import com.mateogallardo.nytimesarticles.data.model.Article

interface ArticleRepository {
    fun addArticle(article: Article)
    fun addArticles(articles: List<Article>)
    fun getArticles(): LiveData<List<Article>>
}