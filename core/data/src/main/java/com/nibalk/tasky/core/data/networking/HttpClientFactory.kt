package com.nibalk.tasky.core.data.networking

import com.nibalk.tasky.core.data.networking.interceptors.ApiKeyInterceptor
import com.nibalk.tasky.core.data.networking.interceptors.LoggingInterceptor
import com.nibalk.tasky.core.data.networking.interceptors.TokenInterceptor
import okhttp3.OkHttpClient

class HttpClientFactory(
    private val apiKeyInterceptor: ApiKeyInterceptor,
    private val loggingInterceptor: LoggingInterceptor,
    private val tokenInterceptor: TokenInterceptor,
) {
    fun build(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }
}
