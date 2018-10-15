package com.mateogallardo.nytimesarticles

import android.arch.lifecycle.Observer
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.mateogallardo.nytimesarticles.data.api.HttpService
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository
import com.mateogallardo.nytimesarticles.data.repository.ArticlesInfo
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
        httpService = Injector.getHttpService(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun shouldAddArticleSuccessfully() {
        val article = TestsHelper.createArticle()
        val articleRepository = ArticleRepository.getInstance(articleDao!!, httpService!!)
        val observer = Observer<ArticlesInfo> {
            //Stub
        }
        articleRepository?.getArticles()?.observeForever(observer)
        articleRepository?.addArticle(article)

        assert(articleRepository?.getArticles()?.value?.articles?.contains(article)!!)
    }
}