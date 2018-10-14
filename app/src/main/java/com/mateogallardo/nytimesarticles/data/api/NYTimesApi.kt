package com.mateogallardo.nytimesarticles.data.api

import retrofit2.http.GET
import retrofit2.Call

const val API_KEY: String = "cc952e3d72b34428a9f16497ef88d113"

interface NYTimesApi {

    @GET("articlesearch.json?api-key=$API_KEY&fl=multimedia,headline,word_count,_id&begin_date=20181014")
    fun getArticles(): Call<ArticlesApiResponse>
}
