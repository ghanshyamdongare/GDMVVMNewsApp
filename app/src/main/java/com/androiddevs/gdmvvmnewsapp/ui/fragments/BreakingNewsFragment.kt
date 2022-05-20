package com.androiddevs.gdmvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.androiddevs.gdmvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.gdmvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.androiddevs.gdmvvmnewsapp.ui.NewsViewModel
import com.androiddevs.gdmvvmnewsapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentBreakingNewsBinding
    lateinit var newsAdapter: NewsAdapter
    val TAG: String = BreakingNewsFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        showDataOnView()
        getAllDataSQlite()

        newsAdapter.setOnItemClickListener {
            Log.d(TAG, "Clicked item title is : " + it.title)
            viewModel.upsertArticle(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvBreakingNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            newsAdapter = NewsAdapter(requireContext()) { article: Article ->
                findNavController().navigate(
                    BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleNewsFragment(
                        article
                    )
                )
            }

            adapter = newsAdapter
        }

    }

    private fun showDataOnView() {
        viewModel.breakingNewsList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {
                    showProgressBar()
                }

                Status.SUCCESS -> {
                    hideProgressBar()
                    result.data?.let { newsAdapter.submitList(it.articles) }
                }
            }
        }
    }

    private fun getAllDataSQlite(){
        viewModel.allDBData.observe(viewLifecycleOwner){ result ->
            Log.d(TAG, result.toString())
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        binding.rvBreakingNews.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}