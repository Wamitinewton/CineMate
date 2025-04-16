package com.newton.network.data.interceptor

import okhttp3.*

class RequestInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val newUrl: HttpUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val requestBuilder = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(requestBuilder)
    }

}