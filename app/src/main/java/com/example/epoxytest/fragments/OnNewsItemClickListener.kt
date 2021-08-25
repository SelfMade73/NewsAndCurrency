package com.example.epoxytest.fragments

import android.view.View
import com.example.epoxytest.models.NewsItem

interface OnNewsItemClickListener {
    fun onNewsClick(newsItem: NewsItem )
}