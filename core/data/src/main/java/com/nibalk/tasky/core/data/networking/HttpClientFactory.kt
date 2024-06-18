package com.nibalk.tasky.core.data.networking

import com.nibalk.tasky.core.data.networking.interceptors.ApiKeyInterceptor
import com.nibalk.tasky.core.data.networking.interceptors.LoggingInterceptor
import okhttp3.OkHttpClient

class HttpClientFactory(
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val loggingInterceptor: LoggingInterceptor,
) {
    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
