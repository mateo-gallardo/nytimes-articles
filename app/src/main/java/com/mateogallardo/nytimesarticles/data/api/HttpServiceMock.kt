package com.mateogallardo.nytimesarticles.data.api

import android.content.Context
import com.google.gson.Gson

class HttpServiceMock(private val context: Context) : HttpService {
    override fun getArticles(callback: HttpService.Callback) {
        val jsonString = HttpServiceMockHelper.getStringFromFile(context, "article_search_success.json")
        val articlesApiResponse = Gson().fromJson<ArticlesApiResponse>(jsonString, ArticlesApiResponse::class.java)
        callback.onSuccess(articlesApiResponse)
    }
}