package com.mateogallardo.nytimesarticles.data.database

interface Database {
    fun getArticleDao(): ArticleDao
    fun closeConnection()
}