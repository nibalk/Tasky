package com.nibalk.tasky.auth.data.di

import com.nibalk.tasky.auth.data.EmailAuthPatternValidator
import com.nibalk.tasky.auth.domain.ValidateEmailUseCase
import com.nibalk.tasky.auth.domain.ValidateNameUseCase
import com.nibalk.tasky.auth.domain.ValidatePasswordUseCase
import com.nibalk.tasky.auth.domain.utils.AuthPatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<AuthPatternValidator> {
        EmailAuthPatternValidator
    }

    singleOf(::ValidateNameUseCase)
    singleOf(::ValidateEmailUseCase)
    singleOf(::ValidatePasswordUseCase)
}
