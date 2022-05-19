package com.androiddevs.gdmvvmnewsapp.repository

import com.androiddevs.gdmvvmnewsapp.api.NewsAPI
import com.androiddevs.gdmvvmnewsapp.models.NewsResponse
import com.androiddevs.gdmvvmnewsapp.ui.NewsViewModel
import com.androiddevs.gdmvvmnewsapp.util.Resource
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: NewsAPI) : NewsRepository {

    override suspend fun getBreakingNews(
        country: String,
        pageNumber: Int
    ): Flow<Resource<NewsResponse>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(apiService.getBreakingNews(country, pageNumber)))
        } catch (e: Exception) {
            emit(Resource.error("Check network connection", null))
        }
    }

}