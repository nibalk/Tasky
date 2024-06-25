package com.nibalk.tasky.core.data.di

import com.nibalk.tasky.core.data.auth.AutoTokenRefresher
import com.nibalk.tasky.core.data.auth.EncryptedSessionStorage
import com.nibalk.tasky.core.data.networking.HttpClientFactory
import com.nibalk.tasky.core.data.networking.JsonConverterFactoryProvider
import com.nibalk.tasky.core.data.networking.RetrofitFactory
import com.nibalk.tasky.core.data.networking.interceptors.ApiKeyInterceptor
import com.nibalk.tasky.core.data.networking.interceptors.LoggingInterceptor
import com.nibalk.tasky.core.data.networking.interceptors.TokenInterceptor
import com.nibalk.tasky.core.domain.auth.SessionStorage
import com.nibalk.tasky.core.domain.auth.TokenRefresher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::ApiKeyInterceptor)
    singleOf(::LoggingInterceptor)
    singleOf(::TokenInterceptor)

    single { JsonConverterFactoryProvider }

    singleOf(::HttpClientFactory)
    singleOf(::RetrofitFactory)

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::AutoTokenRefresher).bind<TokenRefresher>()
}
