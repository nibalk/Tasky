package com.nibalk.tasky.auth.data.di

import com.nibalk.tasky.auth.data.AuthRepositoryImpl
import com.nibalk.tasky.auth.data.EmailAuthPatternValidator
import com.nibalk.tasky.auth.data.remote.AuthApi
import com.nibalk.tasky.auth.domain.AuthRepository
import com.nibalk.tasky.auth.domain.utils.AuthPatternValidator
import com.nibalk.tasky.core.data.networking.RetrofitFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<AuthPatternValidator> {
        EmailAuthPatternValidator
    }

    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    single<AuthApi> {
        get<RetrofitFactory>().build().create(AuthApi::class.java)
    }
}
