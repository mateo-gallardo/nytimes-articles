package com.mateogallardo.nytimesarticles.data.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.mateogallardo.nytimesarticles.data.model.Article
import com.mateogallardo.nytimesarticles.observeOnce

class RoomArticleDao private constructor(private val articleDao: ArticleDao) : ArticleDao {
    private val dbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")
    private val articles = MutableLiveData<List<Article>>()

    init {
        dbWorkerThread.start()
    }

    override fun addArticle(article: Article) {
        val task = Runnable {
            articleDao.addArticle(article)
        }
        dbWorkerThread.postTask(task)
    }

    override fun getArticles(): LiveData<List<Article>> {
        val task = Runnable {
            articleDao.getArticles().observeOnce(Observer { articlesList ->
                articles.postValue(articlesList)
            })
        }
        dbWorkerThread.postTask(task)

        return articles
    }

    companion object {
        @Volatile
        private var instance: RoomArticleDao? = null

        fun getInstance(articleDao: ArticleDao): RoomArticleDao? =
                instance ?: synchronized(this) {
                    instance ?: RoomArticleDao(articleDao).also { instance = it }
                }
    }
}