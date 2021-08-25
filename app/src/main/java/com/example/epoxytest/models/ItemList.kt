package com.example.epoxytest.models

class ItemList(
    val list : List<CurrencyItem>
) {
    operator fun minus( other : ItemList) : ItemList {
        val result = mutableListOf<CurrencyItem>()
        this.list.zip(other.list){ itActual, itYesterday ->
            result.add( CurrencyItem(itActual.name,
                itActual.price,
                itActual.date,
                Math.round((itActual.price - itYesterday.price) * 1000.0)/1000.0)
            )
        }
        return ItemList(result)
    }
}

class NewsList(
    val list : List<NewsItem>
)