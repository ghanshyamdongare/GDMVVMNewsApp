package com.androiddevs.gdmvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.test.services.events.TimeStamp
import java.io.Serializable
import java.util.*

@Entity(tableName = "articles")

data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    var savedDateTime: Long
) : Serializable