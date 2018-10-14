package com.mateogallardo.nytimesarticles

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.test.InstrumentationRegistry
import com.mateogallardo.nytimesarticles.data.api.HttpService
import com.mateogallardo.nytimesarticles.data.api.HttpServiceMock
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ArticleRepositoryTest {
    private var db: Database? = null
    private var articleDao: ArticleDao? = null
    private var httpService: HttpService? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Injector.getDatabaseImplementation(context, true)
        articleDao = db?.getArticleDao()
        httpService = Injector.getHttpService(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun shouldAddArticleSuccessfully() {
        val articleRepository = ArticleRepository.getInstance(articleDao!!, httpService!!)
        Assert.assertTrue(false)
    }
}