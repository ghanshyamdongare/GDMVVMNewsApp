package com.androiddevs.gdmvvmnewsapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.androiddevs.gdmvvmnewsapp.models.NewsResponse
import com.androiddevs.gdmvvmnewsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // API operations
    suspend fun getBreakingNews(country: String, pageNumber: Int): Flow<Resource<NewsResponse>>

    // database operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Flow<Long>

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<LiveData<List<Article>>>

    @Delete
    suspend fun deleteArticle(article: Article)
}

