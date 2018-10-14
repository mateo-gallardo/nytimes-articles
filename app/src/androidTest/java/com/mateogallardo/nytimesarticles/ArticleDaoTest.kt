package com.mateogallardo.nytimesarticles

import android.support.test.runner.AndroidJUnit4
import com.mateogallardo.nytimesarticles.data.database.Database
import com.mateogallardo.nytimesarticles.data.model.Article
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.InstrumentationRegistry
import com.mateogallardo.nytimesarticles.data.database.ArticleDao
import org.junit.Before
import org.junit.After
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
        val id = "1"
        val title = "Article Title"
        val wordCount = 246
        val imageUrl = "/image/url"
        val article = Article(id, title, wordCount, imageUrl)

        articleDao?.addArticle(article)
        Assert.assertTrue(articleDao?.getArticles()!!.contains(article))
    }
}

