package com.androiddevs.gdmvvmnewsapp.adapter

import android.content.Context
import android.view.LayoutInflater
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
    private val clicked: (Article) -> Unit
) : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(DiffCallback) {
    private var onItemClickListener: ((Article) -> Unit)? = null

    inner class ArticleViewHolder(
        private val binding: ItemArticlePreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article?) {
            Glide.with(binding.root).load(article?.urlToImage).into(binding.ivArticleImage)
            binding.tvTitle.text = article?.title
            binding.tvSource.text = article?.source?.name
            binding.tvDescription.text = article?.description
            binding.tvPublishedAt.text = article?.publishedAt
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
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
