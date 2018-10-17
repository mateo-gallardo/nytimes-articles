package com.mateogallardo.nytimesarticles

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.Nullable
import com.mateogallardo.nytimesarticles.data.model.Article
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class TestsHelper {
    companion object {
        fun createArticle(): Article {
            val id = "1"
            val title = "Article Title"
            val wordCount = 246
            val imageUrl = "/image/url"
            return Article(id, title, wordCount, imageUrl)
        }

        @Throws(InterruptedException::class)
        fun <T> getLiveDataValue(liveData: LiveData<T>): T {
            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(@Nullable o: T?) {
                    data[0] = o
                    latch.countDown()
                    liveData.removeObserver(this)
                }
            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            return data[0] as T
        }
    }
}