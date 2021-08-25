package com.example.epoxytest.net

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(
        private val apiKey : String,
        private val queryKey : String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url()
        val newUrl = originalUrl.newBuilder().addQueryParameter( queryKey, apiKey).build()
        val newRequest = request.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}