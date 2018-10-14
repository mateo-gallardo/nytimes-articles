package com.mateogallardo.nytimesarticles.data.database

class DummyDatabase : Database {

    private val articleDao: ArticleDao = DummyArticleDao()

    override fun getArticleDao(): ArticleDao {
        return articleDao
    }
}