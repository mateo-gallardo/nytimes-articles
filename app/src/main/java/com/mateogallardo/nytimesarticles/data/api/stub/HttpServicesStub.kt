package com.mateogallardo.nytimesarticles.data.api.stub

import android.content.Context
import com.google.gson.Gson
import com.mateogallardo.nytimesarticles.data.api.ArticlesApiResponse
import com.mateogallardo.nytimesarticles.data.api.HttpService

class HttpServicesStub(private val context: Context) : HttpService {
    override fun getArticles(callback: HttpService.Callback) {
        val jsonString = HttpServiceStubHelper.getStringFromFile(context, "article_search_success.json")
        val articlesApiResponse = Gson().fromJson<ArticlesApiResponse>(jsonString, ArticlesApiResponse::class.java)
        callback.onSuccess(articlesApiResponse)
    }
}