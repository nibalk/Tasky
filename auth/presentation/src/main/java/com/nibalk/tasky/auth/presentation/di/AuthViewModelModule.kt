package com.nibalk.tasky.auth.presentation.di

import com.nibalk.tasky.auth.domain.usecase.LoginUserUseCase
import com.nibalk.tasky.auth.domain.usecase.RegisterUserUseCase
import com.nibalk.tasky.auth.domain.usecase.ValidateEmailUseCase
import com.nibalk.tasky.auth.domain.usecase.ValidateNameUseCase
import com.nibalk.tasky.auth.domain.usecase.ValidatePasswordUseCase
import com.nibalk.tasky.auth.presentation.login.LoginViewModel
import com.nibalk.tasky.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authUseCaseModule = module {
    singleOf(::ValidateNameUseCase)
    singleOf(::ValidateEmailUseCase)
    singleOf(::ValidatePasswordUseCase)
    singleOf(::RegisterUserUseCase)
    singleOf(::LoginUserUseCase)
}

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}


