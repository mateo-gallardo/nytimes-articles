package com.mateogallardo.nytimesarticles.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository

class ArticleViewModelFactory(private val articleRepository: ArticleRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(articleRepository) as T
    }
}