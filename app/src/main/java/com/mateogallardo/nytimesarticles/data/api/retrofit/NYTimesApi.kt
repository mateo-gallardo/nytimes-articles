package com.mateogallardo.nytimesarticles.data.api.retrofit

import com.mateogallardo.nytimesarticles.data.api.ArticlesApiResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface NYTimesApi {

    @GET("articlesearch.json")
    fun getArticles(@Query("api-key") apiKey: String,
                    @Query("begin_date") date: String,
                    @Query("fl") filter: String): Call<ArticlesApiResponse>
}
