package com.androiddevs.gdmvvmnewsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getBreakingNews("in", 1)
    }

    fun getBreakingNews(countryCode: String, breakingNewsPage: Int) {
        viewModelScope.launch {
            newsRepository.getBreakingNews(countryCode, breakingNewsPage).collect {
                _breakingNewsList.value = it;
            }
        }
    }
}

//    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
//        breakingNews.postValue(Resource.loading(null))
//        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
//        breakingNews.postValue(handleBreakingNewsResponse(response))
//    }

//    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                return Resource.Success(resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }
