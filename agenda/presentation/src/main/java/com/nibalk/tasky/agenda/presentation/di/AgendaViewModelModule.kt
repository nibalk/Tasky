package com.nibalk.tasky.agenda.presentation.di

import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.agenda.presentation.event.EventViewModel
import com.nibalk.tasky.agenda.presentation.home.HomeViewModel
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.reminder.ReminderViewModel
import com.nibalk.tasky.agenda.presentation.task.TaskViewModel
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
    viewModel { (agendaArgs: AgendaArgs) -> EventViewModel(agendaArgs) }
    viewModel { (agendaArgs: AgendaArgs) -> TaskViewModel(agendaArgs) }
    viewModel { (agendaArgs: AgendaArgs) -> ReminderViewModel(agendaArgs)}
}
