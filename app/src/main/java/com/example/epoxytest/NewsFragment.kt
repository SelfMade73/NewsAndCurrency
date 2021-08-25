package com.example.epoxytest

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.epoxytest.databinding.NewsFragmentBinding
import com.example.epoxytest.fragments.OnOpenSourceListener
import com.example.epoxytest.models.NewsItem

class NewsFragment : Fragment() , OnOpenSourceListener {

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val newsItem = requireArguments().getParcelable<NewsItem>( "item" )
        val binding: NewsFragmentBinding = DataBindingUtil.inflate( inflater, R.layout.news_fragment, container,false)
        binding.item = newsItem
        binding.openBrowserListener  = this
        viewModel = ViewModelProvider(this, NewsViewModelFactory(newsItem!!)).get(NewsViewModel::class.java)

        return binding.root
    }

    override fun onClick(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }catch ( e : ActivityNotFoundException){
            Toast.makeText(requireContext(),"Browser not found", Toast.LENGTH_SHORT).show()
        }
    }
}