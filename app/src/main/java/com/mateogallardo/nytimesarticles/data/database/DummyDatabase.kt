package com.mateogallardo.nytimesarticles.data.database

class DummyDatabase : Database {
    override val articleDao: ArticleDao = DummyArticleDao()
}