package com.example.epoxytest.net.news_api

import com.example.epoxytest.models.Category
import com.example.epoxytest.models.NewsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import javax.inject.Inject


class NewsRepositoryImpl
        @Inject constructor(
              private val newsService: NewsService
        )
    : NewsRepository {
    override fun getNewsByCategory(
        country : NewsCountry, category: Category
    ) : Flow<List<NewsItem>> = flow {
        val news = newsService.getBreakingNews( country = country.country, category = category.category )
        emit( news.list )
    }.flowOn( Dispatchers.IO )

    @ExperimentalCoroutinesApi
    override fun getNewsByListOfCategories(
        country: NewsCountry,
        categories: List<Category>
    ): Flow<List<NewsItem>> = categories.map {
            getNewsByCategory(country = country, category = it)
        }.merge()


}