package com.androiddevs.gdmvvmnewsapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.androiddevs.gdmvvmnewsapp.models.NewsResponse
import com.androiddevs.gdmvvmnewsapp.repository.NewsRepository
import com.androiddevs.gdmvvmnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _breakingNewsList: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsList: LiveData<Resource<NewsResponse>> = _breakingNewsList

    private val TAG = NewsViewModel::class.java.simpleName;

    private var _allDBData: MutableLiveData<List<Article>> = MutableLiveData()
    var allDBData: LiveData<List<Article>> = _allDBData

    init {
        getBreakingNews("in", 1)
        // Fetch all record from DB
        getAllDBRecords()
    }

    fun getBreakingNews(countryCode: String, breakingNewsPage: Int) {
        viewModelScope.launch {
            newsRepository.getBreakingNews(countryCode, breakingNewsPage).collect {
                _breakingNewsList.value = it
            }
        }
    }

    fun upsertArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.upsert(article).collect { dbResponse ->
                Log.e(TAG, "Article saved successfully with DB response :  $dbResponse")
            }
        }
    }

    private fun getAllDBRecords() {
        viewModelScope.launch {
            newsRepository.getAllArticles().collect {
                Log.e(TAG, "Article fetched from SQLite DB" + it.toString())
                allDBData = it
            }
        }
    }
}

