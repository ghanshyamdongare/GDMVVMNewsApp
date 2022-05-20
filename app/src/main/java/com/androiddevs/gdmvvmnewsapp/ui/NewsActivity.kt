package com.androiddevs.gdmvvmnewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androiddevs.gdmvvmnewsapp.databinding.ActivityNewsBinding
import com.androiddevs.gdmvvmnewsapp.db.ArticleDatabase
import com.androiddevs.gdmvvmnewsapp.repository.NewsRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
