package com.mateogallardo.nytimesarticles.data.api.retrofit

import com.mateogallardo.nytimesarticles.data.api.ArticlesApiResponse
import com.mateogallardo.nytimesarticles.data.api.HttpService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL: String = "https://api.nytimes.com/svc/search/v2/"
const val API_KEY: String = "cc952e3d72b34428a9f16497ef88d113"
const val FILTER: String = "multimedia,headline,word_count,_id"

class RetrofitHttpService : HttpService {
    override fun getArticles(callback: HttpService.Callback) {
        val httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        val service = retrofit.create(NYTimesApi::class.java)
        val formatter = SimpleDateFormat("YYYYMMdd")
        val dateString = formatter.format(Date())
        val callAsync: Call<ArticlesApiResponse> = service.getArticles(API_KEY, dateString, FILTER)
        callAsync.enqueue(object : Callback<ArticlesApiResponse> {
            override fun onResponse(call: Call<ArticlesApiResponse>, response: Response<ArticlesApiResponse>) {
                val articlesResponse: ArticlesApiResponse? = response.body()
                callback.onSuccess(articlesResponse!!)
            }

            override fun onFailure(call: Call<ArticlesApiResponse>, throwable: Throwable) {
                var message = "Generic error"

                if (throwable.message != null) {
                    message = throwable.message!!
                }

                callback.onError(message)
            }
        })
    }
}