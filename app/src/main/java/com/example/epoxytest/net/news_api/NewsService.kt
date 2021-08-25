package com.example.epoxytest.net.news_api


import com.example.epoxytest.models.NewsList
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    // api_key = b97bf758eb7b42e7a6011d587a8fd745

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
            @Query("country") country : String,
            @Query("category") category : String
    ) : NewsList
}