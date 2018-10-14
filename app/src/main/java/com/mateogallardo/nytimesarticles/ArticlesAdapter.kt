package com.mateogallardo.nytimesarticles

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mateogallardo.nytimesarticles.data.model.Article
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class ArticlesAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(context).inflate(R.layout.article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.wordCount.text = "Word count: ${article.wordCount}"
        Picasso.get().load(article.imageUrl)
                .placeholder(R.drawable.placeholder)
                .transform(CropCircleTransformation())
                .into(holder.thumbnail)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val wordCount: TextView = itemView.findViewById(R.id.word_count)
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    }
}