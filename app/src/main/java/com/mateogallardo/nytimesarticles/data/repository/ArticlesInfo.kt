package com.mateogallardo.nytimesarticles.data.repository

import com.mateogallardo.nytimesarticles.data.model.Article

class ArticlesInfo(var articles: List<Article> = emptyList(), var requestError: Boolean = false)