package com.mateogallardo.nytimesarticles

import android.arch.lifecycle.Observer
import android.view.View
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mateogallardo.nytimesarticles.data.database.DummyArticleDao
import com.mateogallardo.nytimesarticles.data.model.Article
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository
import com.mateogallardo.nytimesarticles.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_articles.*
import android.view.Menu
import android.view.MenuItem

class ArticlesActivity : AppCompatActivity() {
    private val adapterArticles: MutableList<Article> = mutableListOf()
    private var viewModel: ArticleViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        articles_list.layoutManager = LinearLayoutManager(this)
        articles_list.adapter = ArticlesAdapter(adapterArticles)

        viewModel = ArticleViewModel(ArticleRepository(DummyArticleDao()))
        viewModel?.getArticles()?.observe(this, Observer { articles ->
            adapterArticles.clear()
            adapterArticles.addAll(articles!!)
            articles_list.adapter.notifyDataSetChanged()
            progress_bar.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.refresh_button) {
            progress_bar.visibility = View.VISIBLE
            viewModel?.getArticles()
        }

        return super.onOptionsItemSelected(item)
    }
}
