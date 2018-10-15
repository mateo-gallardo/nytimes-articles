package com.mateogallardo.nytimesarticles

import com.mateogallardo.nytimesarticles.data.model.Article

class TestsHelper {
    companion object {
        fun createArticle(): Article {
            val id = "1"
            val title = "Article Title"
            val wordCount = 246
            val imageUrl = "/image/url"
            return Article(id, title, wordCount, imageUrl)
        }
    }
}