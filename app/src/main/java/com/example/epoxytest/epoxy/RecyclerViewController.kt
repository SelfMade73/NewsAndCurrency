package com.example.epoxytest.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.example.epoxytest.*
import com.example.epoxytest.fragments.Loading
import com.example.epoxytest.fragments.OnNewsItemClickListener
import com.example.epoxytest.models.*


class RecyclerViewController(
    private var onCategoryChanged: View.OnLayoutChangeListener,
    private var onNewsItemClickListener: OnNewsItemClickListener
    ) : EpoxyController() {

    var newsItems : List<NewsItem> = listOf()
        set(value) {
            field = value
            requestModelBuild()
        }

    var state = Loading
        set(value) {
            field = value
            requestModelBuild()
        }
    var currencyItems : List<CurrencyItem> = listOf()
        set(value){
            field = value
            requestModelBuild()
        }

    var categories : Categories = Categories(listOf())
        set(value){
            field = value
            requestModelBuild()
        }


    override fun buildModels() {
        header {
            id ( "Currency header")
            title("Currency rates")
        }
        carousel {
            id ( "Rates carousel")
            withModelsFrom(this@RecyclerViewController.currencyItems){
                CurrencyBindingModel_()
                    .id(it.hashCode())
                    .item(it)
            }
        }

        header {
            id("News header")
            title("News feed")
        }

        category {
            id("Category carousel")
            items( this@RecyclerViewController.categories )
            onChangeListener(this@RecyclerViewController.onCategoryChanged)
        }

        this.newsItems.forEach {
            news {
                id(it.hashCode())
                newsItem(it)
                listener(this@RecyclerViewController.onNewsItemClickListener)
            }
        }
    }
}