package com.mateogallardo.nytimesarticles.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Article(@PrimaryKey val id: String, val title: String, val wordCount: Int, val imageUrl: String?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        return id != other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}