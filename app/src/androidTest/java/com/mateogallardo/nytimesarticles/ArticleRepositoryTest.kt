package com.mateogallardo.nytimesarticles

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mateogallardo.nytimesarticles.data.api.HttpService
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleRepositoryTest {
    private var db: Database? = null
    private var articleDao: ArticleDao? = null
    private var httpService: HttpService? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Injector.getDatabaseImplementation(context, true)
        articleDao = db?.getArticleDao()
        httpService = Injector.getHttpService(InstrumentationRegistry.getInstrumentation().context, true)
    }

    @Test
    fun shouldAddArticleSuccessfully() {
        val article = TestsHelper.createArticle()
        val articleRepository = ArticleRepository.getInstance(articleDao!!, httpService!!)
        articleRepository?.addArticle(article)
        assertTrue(TestsHelper.getLiveDataValue(articleRepository?.getArticles()!!).articles.contains(article))
    }
}