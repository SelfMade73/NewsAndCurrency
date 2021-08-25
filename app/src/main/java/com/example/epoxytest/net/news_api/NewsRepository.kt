package com.example.epoxytest.net.news_api

import com.example.epoxytest.models.Categories
import com.example.epoxytest.models.Category
import com.example.epoxytest.models.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsByCategory(country : NewsCountry, category: Category) : Flow<List<NewsItem>>

    fun getNewsByListOfCategories( country: NewsCountry, categories: List<Category> ) : Flow<List<NewsItem>>
}