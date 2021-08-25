package com.example.epoxytest.di

import com.example.epoxytest.db.AppDatabase
import com.example.epoxytest.db.RatesDao
import com.example.epoxytest.net.*
import com.example.epoxytest.net.currency_api.CurrencyDeserializer
import com.example.epoxytest.net.currency_api.CurrencyService
import com.example.epoxytest.net.currency_api.RatesRepository
import com.example.epoxytest.net.currency_api.RatesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


@Module
@InstallIn( SingletonComponent::class )
class CurrencyModule {

    @Provides
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    fun provideOkHttpClient(
                logger : HttpLoggingInterceptor,
                @CurrencyApi apiKeyInterceptor: ApiKeyInterceptor
            ) : OkHttpClient =
                    OkHttpClient.Builder()
                    .connectTimeout(5 , TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(2, TimeUnit.SECONDS)
                    .addInterceptor( logger )
                    .addInterceptor(apiKeyInterceptor)
                    .build()

    @CurrencyApi
    @Provides
    fun provideRetrofit( client : OkHttpClient ) : Retrofit =
                Retrofit.Builder()
                .baseUrl("https://openexchangerates.org/api/")
                .addConverterFactory(CurrencyDeserializer.createConverter())
                .client(client)
                .build()


    @Provides
    fun provideCurrencyService( @CurrencyApi retrofit: Retrofit ): CurrencyService = retrofit.create(CurrencyService::class.java)


    @CurrencyApi
    @Provides
    fun provideCurrencyApiKeyInterceptor() : ApiKeyInterceptor = ApiKeyInterceptor(
            apiKey = "d06aaf2e8df0401cbfe687e58ac5b8b6",
            queryKey = "app_id"
    )

    @Provides
    fun provideRatesRepository(
            currencyService: CurrencyService,
            ratesDao: RatesDao
    ) : RatesRepository {
        return RatesRepositoryImpl( currencyService,ratesDao)
    }

    @Provides
    fun provideRatesDao( db : AppDatabase) = db.ratesDao()

}