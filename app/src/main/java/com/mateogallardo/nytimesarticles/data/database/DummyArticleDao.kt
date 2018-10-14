package com.mateogallardo.nytimesarticles.data.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mateogallardo.nytimesarticles.data.model.Article

class DummyArticleDao : ArticleDao {
    private val articleList = mutableListOf<Article>()
    private val articles = MutableLiveData<List<Article>>()

    init {
        articles.value = articleList
    }

    override fun addArticle(article: Article) {
        articleList.add(article)
        articles.value = articleList
    }

    override fun getArticles() = articles as LiveData<List<Article>>
}