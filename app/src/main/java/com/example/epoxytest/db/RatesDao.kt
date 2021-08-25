package com.example.epoxytest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.epoxytest.models.Category
import com.example.epoxytest.models.CurrencyItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertRates( rates : List<CurrencyItem> )

    @Query("SELECT * FROM currencyitem ORDER BY name DESC")
    fun getRates(): Flow<List<CurrencyItem>>
}