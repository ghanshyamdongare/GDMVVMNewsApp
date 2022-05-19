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
    }
}


//=====================================================================

//
//class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
//
//    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
//        return ArticleViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent,false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
//        val article = differ.currentList[position]
//        holder.itemView.apply {
//            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
//            tvSource.text = article.source.name
//            tvDescription.text = article.description
//            tvPublishedAt.text = article.publishedAt
//
//            onItemClickListener?.let {
//                it(article)
//            }
//        }
//    }
//
//    private var onItemClickListener :((Article)-> Unit)? = null
//
//    fun setOnItemClickListener(listener : (Article) -> Unit){
//        onItemClickListener = listener
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    private val differentCallback = object : DiffUtil.ItemCallback<Article>() {
//        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
//            return oldItem.url == newItem.url
//        }
//
//        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
//            return oldItem == newItem
//        }
//    }
//
//    val differ = AsyncListDiffer(this,differentCallback)
//
//}