package com.mateogallardo.nytimesarticles.data.api

interface HttpService {
    fun getArticles(callback: Callback)

    interface Callback {
        fun onSuccess(articlesApiResponse: ArticlesApiResponse)
        fun onError(errorMessage: String)
    }
}