package com.androiddevs.gdmvvmnewsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.androiddevs.gdmvvmnewsapp.api.NewsAPI
import com.androiddevs.gdmvvmnewsapp.db.ArticleDatabase
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.androiddevs.gdmvvmnewsapp.models.NewsResponse
import com.androiddevs.gdmvvmnewsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsAPI, private val database: ArticleDatabase
) : NewsRepository {
    val TAG: String = NewsRepositoryImpl::class.java.simpleName

    // Repository API calls
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

    // Repository database calls
    override suspend fun upsert(article: Article): Flow<Long> = flow {
        try {
            val returnValue: Long = database.getArticleDao().upsert(article)
            emit(returnValue)
        } catch (e: Exception) {
            emit(-1.toLong())
        }
    }

    override fun getAllArticles(): Flow<LiveData<List<Article>>> = flow {
        try {
            val allArticles: LiveData<List<Article>> =
                database.getArticleDao().getAllArticles()
            emit(allArticles)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    override suspend fun deleteArticle(article: Article) {
        val deletedInt: Int = database.getArticleDao().deleteArticle(article)
        Log.e(TAG, "Successfully deleted the Article from DB $deletedInt")
    }

}