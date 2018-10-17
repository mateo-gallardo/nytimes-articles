package com.mateogallardo.nytimesarticles

import android.support.test.runner.AndroidJUnit4
import com.mateogallardo.nytimesarticles.data.database.Database
import org.junit.runner.RunWith
import android.support.test.InstrumentationRegistry
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import junit.framework.Assert.assertTrue
import org.junit.*
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {
    private var db: Database? = null
    private var articleDao: ArticleDao? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Injector.getDatabaseImplementation(context, true)
        articleDao = db?.getArticleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db?.closeConnection()
    }

    @Test
    fun shouldAddArticleSuccessfully() {
        val article = TestsHelper.createArticle()
        articleDao?.addArticle(article)
        assertTrue(TestsHelper.getLiveDataValue(articleDao?.getArticles()!!).contains(article))
    }
}

