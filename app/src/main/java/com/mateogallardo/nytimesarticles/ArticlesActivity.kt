package com.mateogallardo.nytimesarticles

import android.arch.lifecycle.Observer
import android.view.View
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mateogallardo.nytimesarticles.data.model.Article
import com.mateogallardo.nytimesarticles.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_articles.*
import android.view.Menu
import android.view.MenuItem
import com.mateogallardo.nytimesarticles.data.repository.ArticleRepository

class ArticlesActivity : AppCompatActivity() {
    private val adapterArticles: MutableList<Article> = mutableListOf()
    private var viewModel: ArticleViewModel? = null
    private var articleRepository: ArticleRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        articles_list.layoutManager = LinearLayoutManager(this)
        articles_list.adapter = ArticlesAdapter(adapterArticles)
        connection_error.visibility = View.GONE
        val db = Injector.getDatabaseImplementation(this)
        val httpService = Injector.getHttpService(this)

        articleRepository = ArticleRepository.getInstance(db.getArticleDao(), httpService)
        viewModel = ArticleViewModel(articleRepository!!)
        viewModel?.getArticles()?.observe(this, Observer { articlesInfo ->
            if (articlesInfo?.articles != null) {
                sortArticles(articlesInfo.articles)
                adapterArticles.clear()
                adapterArticles.addAll(articlesInfo.articles)
            }

            if (articlesInfo?.requestError!!) {
                connection_error.visibility = View.VISIBLE
            } else {
                connection_error.visibility = View.GONE
            }

            articles_list.adapter.notifyDataSetChanged()
            progress_bar.visibility = View.GONE
        })
    }

    private fun sortArticles(articles: List<Article>) {
        articles.sortedWith(Comparator{ a1, a2 ->
            if (a1.id > a2.id) {
                return@Comparator 1
            } else {
                return@Comparator -1
            }
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

    override fun onDestroy() {
        Injector.getDatabaseImplementation(this).closeConnection()
        articleRepository?.onDestroy()
        super.onDestroy()
    }
}
