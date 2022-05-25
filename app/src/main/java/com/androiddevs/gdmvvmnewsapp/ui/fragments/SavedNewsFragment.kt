package com.androiddevs.gdmvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.gdmvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.gdmvvmnewsapp.databinding.FragmentSavedNewsBinding
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.androiddevs.gdmvvmnewsapp.ui.viewModels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    lateinit var newsAdapter: NewsAdapter
    private val TAG: String = SavedNewsFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getAllDataSQlite()
    }

    private fun setupRecyclerView() {
        binding.rvSavedNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            newsAdapter = NewsAdapter(requireContext(), false) { article: Article ->
                Log.d(TAG, article.title)
            }
            adapter = newsAdapter
        }
    }

    private fun getAllDataSQlite() {
        viewModel.allDBData.observe(viewLifecycleOwner) { result ->

            if (result.size > 0) {
                showDataOnRecyclerView(true)
                newsAdapter.submitList(result)
            } else {
                showDataOnRecyclerView(false)
            }
        }
    }

    private fun showDataOnRecyclerView(isDataAvailable: Boolean) {
        if (isDataAvailable) {
            binding.rvSavedNews.visibility = View.VISIBLE
            binding.tvNodata.visibility = View.GONE
        } else {
            binding.rvSavedNews.visibility = View.GONE
            binding.tvNodata.visibility = View.VISIBLE
        }

    }
}