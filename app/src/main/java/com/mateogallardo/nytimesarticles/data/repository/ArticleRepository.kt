package com.mateogallardo.nytimesarticles.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mateogallardo.nytimesarticles.DbWorkerThread
import com.mateogallardo.nytimesarticles.data.api.ArticlesApiResponse
import com.mateogallardo.nytimesarticles.data.api.Multimedia
import com.mateogallardo.nytimesarticles.data.api.NYTimesApi
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import com.mateogallardo.nytimesarticles.data.model.Article
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val BASE_URL: String = "https://api.nytimes.com/svc/search/v2/"

class ArticleRepository(private val articleDao: ArticleDao) {
    private val articleList = mutableListOf<Article>()
    private val articlesInfo = MutableLiveData<ArticlesInfo>()
    private val dbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")

    init {
        dbWorkerThread.start()
    }

    fun addArticle(article: Article) {
        articleDao.addArticle(article)
    }

    fun addArticles(articles: List<Article>) {
        articles.forEach { article -> addArticle(article) }
    }

    fun getArticles(): LiveData<ArticlesInfo> {
        val httpClient = OkHttpClient.Builder()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        val service = retrofit.create(NYTimesApi::class.java)
        val callAsync: Call<ArticlesApiResponse> = service.getArticles()
        callAsync.enqueue(object : Callback<ArticlesApiResponse> {
            override fun onResponse(call: Call<ArticlesApiResponse>, response: Response<ArticlesApiResponse>) {
                val articlesResponse: ArticlesApiResponse? = response.body()
                articleList.clear()
                articlesResponse?.response?.docs?.forEach {
                    val article = Article(it._id, it.headline.main, it.word_count, getImageUrl(it.multimedia))
                    articleList.add(article)
                }

                articlesInfo.value = ArticlesInfo(articleList)
                val task = Runnable {
                    addArticles(articleList)
                }
                dbWorkerThread.postTask(task)
            }

            override fun onFailure(call: Call<ArticlesApiResponse>, throwable: Throwable) {
                val task = Runnable {
                    val newArticlesInfo = ArticlesInfo(articleDao.getArticles(), true)
                    articlesInfo.postValue(newArticlesInfo)
                }
                dbWorkerThread.postTask(task)
            }
        })

        return articlesInfo
    }

    private fun getImageUrl(multimediaArray: Array<Multimedia>): String? {
        var imageUrl: String? = null

        for (multimedia in multimediaArray) {
            if (multimedia.subtype == "thumbnail") {
                imageUrl = "http://www.nytimes.com/" + multimedia.url
                break
            }
        }

        return imageUrl
    }
}
