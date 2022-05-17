package com.androiddevs.gdmvvmnewsapp.repository

import com.androiddevs.gdmvvmnewsapp.api.RetrofitInstance
import com.androiddevs.gdmvvmnewsapp.db.ArticleDatabase

class NewsRepository(val db: ArticleDatabase) {
suspend fun getBreakingNews(countryCode: String, pageNumber : Int) = RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
}