package com.nibalk.tasky.agenda.presentation.di

import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.agenda.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val agendaUseCaseModule = module {
    singleOf(::FormatProfileNameUseCase)
    singleOf(::GetAgendasUseCase)
}

val agendaViewModelModule = module {
    viewModelOf(::HomeViewModel)
}


