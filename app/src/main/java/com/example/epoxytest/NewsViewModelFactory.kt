package com.example.epoxytest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.epoxytest.models.NewsItem

class NewsViewModelFactory(
    private val newsItem: NewsItem
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if ( modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(newsItem) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}