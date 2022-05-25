package com.androiddevs.gdmvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.gdmvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.gdmvvmnewsapp.databinding.FragmentBreakingNewsBinding
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.androiddevs.gdmvvmnewsapp.ui.viewModels.NewsViewModel
import com.androiddevs.gdmvvmnewsapp.util.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

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

        newsAdapter.setOnSaveClickListener {
            it.savedDateTime = Calendar.getInstance().time.time
            // Save article in database
            viewModel.upsertArticle(it)
            Snackbar.make(view, "Article saved successfully", Snackbar.ANIMATION_MODE_SLIDE).show()
        }
    }

    private fun setupRecyclerView() {
        binding.rvBreakingNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            newsAdapter = NewsAdapter(requireContext(), true) { article: Article ->

//                Log.d(TAG, article.title.toString())
//                val bundle = Bundle().apply {
//                    putSerializable("article", article)
//                }

                findNavController().navigate(BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleDetailsFragment())
//                findNavController().navigate(
//                    R.id.action_breakingNewsFragment_to_articleDetailsFragment,
//                    bundle
//                )
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

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        binding.rvBreakingNews.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


}