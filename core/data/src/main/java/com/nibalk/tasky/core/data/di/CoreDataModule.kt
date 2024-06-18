package com.nibalk.tasky.core.data.di

import com.nibalk.tasky.core.data.networking.HttpClientFactory
import com.nibalk.tasky.core.data.networking.JsonConverterFactoryProvider
import com.nibalk.tasky.core.data.networking.RetrofitFactory
import com.nibalk.tasky.core.data.networking.interceptors.ApiKeyInterceptor
import com.nibalk.tasky.core.data.networking.interceptors.LoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::ApiKeyInterceptor)
    singleOf(::LoggingInterceptor)
    singleOf(::HttpClientFactory)

    single { JsonConverterFactoryProvider }

    single { HttpClientFactory(get(), get()) }
    single {
        get<HttpClientFactory>().build()
    }

    single { RetrofitFactory(get(), get()) }
    single {
        get<RetrofitFactory>().build()
    }
}
