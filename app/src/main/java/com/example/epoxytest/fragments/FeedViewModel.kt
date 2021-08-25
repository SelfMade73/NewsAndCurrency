package com.example.epoxytest.fragments

import android.util.Log
import androidx.lifecycle.*
import com.example.epoxytest.models.Category
import com.example.epoxytest.models.CurrencyItem
import com.example.epoxytest.models.NewsItem
import com.example.epoxytest.net.currency_api.RatesRepository
import com.example.epoxytest.net.news_api.NewsCategory
import com.example.epoxytest.net.news_api.NewsCountry
import com.example.epoxytest.net.news_api.NewsRepository
import com.example.epoxytest.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class FeedViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val newsRepository: NewsRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {


    val state = MutableLiveData<FeedState>().apply { value = Loading }
    val ratesItem = ratesRepository.loadRates().asLiveData()
    val newsItem = MutableLiveData<MutableSet<NewsItem>>().apply { value = mutableSetOf() }
    val categories = categoryRepository.allCategory.asLiveData()
    val toastMsg = MutableLiveData<String>()


    init {
        update()
    }

    fun update(){
        state.value = Loading
        viewModelScope.launch {
            listOf(
                async { loadNews() },
                async { updateRates() }
            ).awaitAll()
        }.invokeOnCompletion {
            it?.let { toastMsg.value = it.message }
            state.value = Success
        }
    }

    private fun updateRates() = viewModelScope.launch(Dispatchers.IO){
            ratesRepository.updateRates()
        }.invokeOnCompletion {
            it?.let { toastMsg.value = it.message }
    }


    private fun loadNews() =
        viewModelScope.launch {
            categories.value?.let {  categories->
                newsRepository.getNewsByListOfCategories(
                    NewsCountry.US, categories = categories.filter { category -> category.checked })
                    .catch { exception-> exception.message?.let { toastMsg.value = it } }
                    .collect { news -> newsItem.value = news.toMutableSet()}
            }
        }

    fun saveCategories(categoryList: List<Category>) {
        viewModelScope.launch {
                categoryRepository.saveCategories(categoryList)
        }
    }


}