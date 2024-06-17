package com.nibalk.tasky.auth.presentation.di

import com.nibalk.tasky.auth.presentation.login.LoginViewModel
import com.nibalk.tasky.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
