package com.androiddevs.gdmvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.gdmvvmnewsapp.repository.NewsRepository

class NewsViewModelProviderFactory(val newsRepository : NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}