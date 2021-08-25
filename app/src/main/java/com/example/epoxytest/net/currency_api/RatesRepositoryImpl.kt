package com.example.epoxytest.net.currency_api

import android.util.Log
import com.example.epoxytest.models.CurrencyItem
import com.example.epoxytest.dateByDaysAgo
import com.example.epoxytest.db.RatesDao
import com.example.epoxytest.net.currency_api.RatesRepository.Companion.currencies
import com.example.epoxytest.toRequiredFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RatesRepositoryImpl
    @Inject constructor(
            private val  currencyService: CurrencyService,
            private val ratesDao: RatesDao
    ) : RatesRepository {

    private var baseCurrency : CurrencyTypes = CurrencyTypes.USD
    private val symbols = currencies.joinToString ( separator = ",", transform = {it.currency} )

    override suspend fun updateRates()  {
        flow {
            val actual = currencyService.getCurrencyInfo( base = baseCurrency.currency, symbols = symbols)
            val yesterday = currencyService.getCurrencyInfoByDate(
                date = dateByDaysAgo(2).toRequiredFormat(),
                base = baseCurrency.currency, symbols = symbols )
        emit((actual - yesterday).list)
        }.flowOn(Dispatchers.IO)
            .catch { throw it }
            .collect {
                Log.d("Test","2")
                ratesDao.insertRates(it)
            }
    }

    override fun loadRates(): Flow<List<CurrencyItem>> = ratesDao.getRates()

}