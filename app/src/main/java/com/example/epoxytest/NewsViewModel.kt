package com.example.epoxytest

import androidx.lifecycle.ViewModel
import com.example.epoxytest.models.NewsItem
import dagger.hilt.android.lifecycle.HiltViewModel


class NewsViewModel(
    private val newsItem : NewsItem
)  : ViewModel() {

}