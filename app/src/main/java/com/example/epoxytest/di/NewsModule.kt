package com.example.epoxytest.di

import com.example.epoxytest.net.ApiKeyInterceptor
import com.example.epoxytest.net.news_api.NewsDeserializer
import com.example.epoxytest.net.news_api.NewsRepository
import com.example.epoxytest.net.news_api.NewsRepositoryImpl
import com.example.epoxytest.net.news_api.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @NewsApi
    @Provides
    fun provideNewsApiKeyInterceptor( ) : ApiKeyInterceptor = ApiKeyInterceptor(
            apiKey = "b97bf758eb7b42e7a6011d587a8fd745",
            queryKey = "apiKey"
    )

    @NewsApi
    @Provides
    fun provideNewsOkHttpClient(
            logger: HttpLoggingInterceptor,
            @NewsApi apiKeyInterceptor: ApiKeyInterceptor
    ) : OkHttpClient{
        return OkHttpClient.Builder()
                .connectTimeout(5 , TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .addInterceptor( logger )
                .addInterceptor(apiKeyInterceptor)
                .build()
    }


    @NewsApi
    @Provides
    fun provideRetrofitClient(
        @NewsApi client: OkHttpClient
    ): Retrofit =  Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(NewsDeserializer.createConverter())
                .client(client)
                .build()


    @Provides
    fun providesNewsService(
            @NewsApi retrofit: Retrofit
    ) : NewsService = retrofit.create( NewsService::class.java )

    @Provides
    fun providesNewsRepository(
        newsService: NewsService
    ) : NewsRepository = NewsRepositoryImpl( newsService )


}