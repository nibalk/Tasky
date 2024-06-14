package com.nibalk.tasky.auth.data.di

import com.nibalk.tasky.auth.data.EmailPatternValidator
import com.nibalk.tasky.auth.domain.PatternValidator
import com.nibalk.tasky.auth.domain.UserDataValidator
import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}
