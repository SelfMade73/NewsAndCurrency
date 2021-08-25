package com.example.epoxytest.net.currency_api

import com.example.epoxytest.models.CurrencyItem
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    companion object{
        val currencies = listOf(
                CurrencyTypes.RUB,
                CurrencyTypes.EURO,
                CurrencyTypes.USD,
                CurrencyTypes.EURO,
                CurrencyTypes.JPY,
                CurrencyTypes.AED, // < United Arab Emirates Dirham
                CurrencyTypes.NZD, // < New Zealand Dollar
                CurrencyTypes.SEK, // < Swedish Krona
                CurrencyTypes.YER
        )
    }

    suspend fun updateRates()
    fun loadRates() : Flow<List<CurrencyItem>>
}