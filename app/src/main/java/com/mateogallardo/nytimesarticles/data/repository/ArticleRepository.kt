package com.mateogallardo.nytimesarticles.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.mateogallardo.nytimesarticles.data.api.*
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import com.mateogallardo.nytimesarticles.data.model.Article
import com.mateogallardo.nytimesarticles.observeOnce

class ArticleRepository private constructor(private val articleDao: ArticleDao, private val httpService: HttpService) {
    private val articleList = mutableListOf<Article>()
    private val articlesInfo = MutableLiveData<ArticlesInfo>()

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(articleDao: ArticleDao, httpService: HttpService): ArticleRepository? =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(articleDao, httpService).also { instance = it }
            }
    }

    fun addArticle(article: Article) {
        articleDao.addArticle(article)
    }

    fun addArticles(articles: List<Article>) {
        articles.forEach { article -> addArticle(article) }
    }

    fun getArticles(): LiveData<ArticlesInfo> {
        httpService.getArticles(object : HttpService.Callback {
            override fun onSuccess(articlesApiResponse: ArticlesApiResponse) {
                articleList.clear()
                articlesApiResponse.response.docs.forEach {
                    val article = Article(it._id, it.headline.main, it.word_count, getImageUrl(it.multimedia))
                    articleList.add(article)
                }

                articlesInfo.value = ArticlesInfo(articleList)
                addArticles(articleList)
            }

            override fun onError(errorMessage: String) {
                articleDao.getArticles().observeOnce(Observer { articles ->
                    articlesInfo.postValue(ArticlesInfo(articles!!, true))
                })
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
