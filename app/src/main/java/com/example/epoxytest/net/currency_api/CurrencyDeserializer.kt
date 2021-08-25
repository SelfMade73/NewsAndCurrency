package com.example.epoxytest.net.currency_api

import com.example.epoxytest.models.CurrencyItem
import com.example.epoxytest.models.ItemList
import com.google.gson.*
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<ItemList> {
    override fun deserialize( json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ItemList {
        val jsonObject = json?.asJsonObject
        jsonObject?.let { obj ->
            val ratesJson = obj.get("rates").asJsonObject
            val requiredPrice : Double = ratesJson.get(CurrencyConfig.requiredCurrency).asDouble
            return ItemList(ratesJson.entrySet()
                .map{ CurrencyItem(it.key,  requiredPrice / it.value.asDouble , obj.get("timestamp").asLong) }
                .filter { it.name != CurrencyConfig.requiredCurrency }
                .sortedBy { it.name })
        }
        return ItemList( listOf() )
    }

    companion object {
        fun createConverter() : Converter.Factory{
            val builder = GsonBuilder()
            builder.registerTypeAdapter(ItemList::class.java, CurrencyDeserializer() )
            return GsonConverterFactory.create(builder.create())
        }
    }

}