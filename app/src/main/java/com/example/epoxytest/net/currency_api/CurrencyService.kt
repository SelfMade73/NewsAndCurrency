package com.example.epoxytest.net.currency_api

import com.example.epoxytest.models.ItemList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CurrencyService {

    @GET("/latest.json")
    suspend fun getCurrencyInfo(
        @Query("base") base : String,
        @Query("symbols") symbols : String
    ) : ItemList

    @GET("/historical/{date}.json")
    suspend fun getCurrencyInfoByDate(
        @Path("date") date : String,
        @Query("base") base : String,
        @Query("symbols") symbols : String
    ) : ItemList

}