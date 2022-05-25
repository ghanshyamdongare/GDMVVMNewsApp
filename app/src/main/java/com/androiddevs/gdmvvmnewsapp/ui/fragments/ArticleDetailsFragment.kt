package com.androiddevs.gdmvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androiddevs.gdmvvmnewsapp.databinding.FragmentArticleDetailsBinding
import com.androiddevs.gdmvvmnewsapp.ui.viewModels.ArticleDetailsViewModel

class ArticleDetailsFragment : Fragment() {
    private val articleDetailsViewModel: ArticleDetailsViewModel by viewModels()
    private lateinit var binding: FragmentArticleDetailsBinding
    val TAG: String = ArticleDetailsFragment::class.java.simpleName
    //private val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //setDataOnView()

      //  requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

//    private fun setDataOnView() {
//        binding.tvTitle.text = args.article.title
//    }
//
//    private val callback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            findNavController().popBackStack()
//        }
//    }

}