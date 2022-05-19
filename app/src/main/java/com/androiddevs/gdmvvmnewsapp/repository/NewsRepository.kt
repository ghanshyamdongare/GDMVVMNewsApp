package com.androiddevs.gdmvvmnewsapp.repository

import com.androiddevs.gdmvvmnewsapp.models.NewsResponse
import com.androiddevs.gdmvvmnewsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getBreakingNews(country:String, pageNumber: Int): Flow<Resource<NewsResponse>>
}

