package com.androiddevs.gdmvvmnewsapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.gdmvvmnewsapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)


abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

}
