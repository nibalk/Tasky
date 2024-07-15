package com.nibalk.tasky.agenda.presentation.di

import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.agenda.presentation.detail.DetailViewModel
import com.nibalk.tasky.agenda.presentation.home.HomeViewModel
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val agendaUseCaseModule = module {
    singleOf(::FormatProfileNameUseCase)
    singleOf(::GetAgendasUseCase)
}

val agendaViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel {
        (agendaArgs: AgendaArgs) -> DetailViewModel(agendaArgs)
    }
}
