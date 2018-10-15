package com.mateogallardo.nytimesarticles.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mateogallardo.nytimesarticles.data.database.DbWorkerThread
import com.mateogallardo.nytimesarticles.data.api.*
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import com.mateogallardo.nytimesarticles.data.model.Article

class ArticleRepository private constructor(private val articleDao: ArticleDao, private val httpService: HttpService) {
    private val articleList = mutableListOf<Article>()
    private val articlesInfo = MutableLiveData<ArticlesInfo>()
    private val dbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")

    init {
        dbWorkerThread.start()
    }

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
                articlesApiResponse?.response?.docs?.forEach {
                    val article = Article(it._id, it.headline.main, it.word_count, getImageUrl(it.multimedia))
                    articleList.add(article)
                }

                articlesInfo.value = ArticlesInfo(articleList)
                val task = Runnable {
                    addArticles(articleList)
                }
                dbWorkerThread.postTask(task)
            }

            override fun onError(errorMessage: String) {
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

    fun onDestroy() {
        dbWorkerThread.quit()
    }
}
