package com.androiddevs.gdmvvmnewsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.gdmvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.androiddevs.gdmvvmnewsapp.models.Article
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsAdapter @Inject constructor(
    @ApplicationContext val context: Context,
    val isFromBreakingNews: Boolean,
    private val clicked: (Article) -> Unit
) : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(DiffCallback) {

    inner class ArticleViewHolder(
        private val binding: ItemArticlePreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?) {
            Glide.with(binding.root).load(article?.urlToImage).into(binding.ivArticleImage)
            binding.tvTitle.text = article?.title
            binding.tvSource.text = article?.source?.name
            binding.tvDescription.text = article?.description
            binding.tvPublishedAt.text = article?.publishedAt
            if (isFromBreakingNews) {
                binding.btnSave.visibility = View.VISIBLE
            } else {
                binding.btnSave.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                article?.let { article -> clicked.invoke(article) }
            }

            binding.btnSave.setOnClickListener {
                onSaveClickListener?.let {
                    if (article != null) {
                        it(article)
                    }
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    private var onSaveClickListener: ((Article) -> Unit)? = null

    fun setOnSaveClickListener(listener: (Article) -> Unit) {
        onSaveClickListener = listener
    }
}
