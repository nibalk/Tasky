package com.nibalk.tasky.core.data.networking

import com.nibalk.tasky.core.data.BuildConfig
import retrofit2.Retrofit

class RetrofitFactory(
    private val httpClientFactory: HttpClientFactory,
    private val jsonConverterFactoryProvider: JsonConverterFactoryProvider,
) {
    fun build(): Retrofit {
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientFactory.build())
                .addConverterFactory(jsonConverterFactoryProvider.provide())
                .build()
        }
        return retrofit
    }
}
