package com.example.epoxytest.net.news_api

import com.example.epoxytest.models.NewsItem
import com.example.epoxytest.models.NewsList
import com.google.gson.*
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class NewsDeserializer : JsonDeserializer<NewsList> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): NewsList {
        json?.let {
            val jsonRoot = it.asJsonObject
            if ( jsonRoot["status"].asString != "ok"){
                return NewsList(listOf())
            }
            val result : MutableList<NewsItem> = ArrayList(jsonRoot["totalResults"].asInt)
            val articles = jsonRoot["articles"].asJsonArray
            articles.map { article -> article.asJsonObject }.forEach { article ->
                result.add( Gson().fromJson(article, NewsItem::class.java) )
            }
            return NewsList(result)
        }
        return NewsList(listOf())
    }

    companion object {
        fun createConverter() : Converter.Factory{
            val builder = GsonBuilder()
            builder.registerTypeAdapter(NewsList::class.java, NewsDeserializer() )
            return GsonConverterFactory.create(builder.create())
        }
    }

}