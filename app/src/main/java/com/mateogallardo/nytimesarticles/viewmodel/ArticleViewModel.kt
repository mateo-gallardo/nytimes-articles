package com.mateogallardo.nytimesarticles.viewmodel

import androidx.lifecycle.ViewModel
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    fun getArticles() = articleRepository.getArticles()
}