package com.mateogallardo.nytimesarticles

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.mateogallardo.nytimesarticles.data.database.DummyArticleDao
import com.mateogallardo.nytimesarticles.data.model.Article
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository
import com.mateogallardo.nytimesarticles.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_articles.*

class ArticlesActivity : AppCompatActivity() {
    private val adapterArticles: MutableList<Article> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        articles_list.layoutManager = LinearLayoutManager(this)
        articles_list.adapter = ArticlesAdapter(adapterArticles)

        val viewModel = ArticleViewModel(ArticleRepository(DummyArticleDao()))
        viewModel.getArticles().observe(this, Observer { articles ->
            adapterArticles.clear()
            adapterArticles.addAll(articles!!)
            Log.i("ARTICLES", "DATA SER CHANGED")
            articles_list.adapter.notifyDataSetChanged()

            articles.forEach { article ->
                Log.i("ARTICLES", "${article.id} ${article.title}")
            }
        })
    }
}
