package com.nibalk.tasky.agenda.presentation.di

import com.nibalk.tasky.agenda.domain.usecase.FetchAgendasByDateUseCase
import com.nibalk.tasky.agenda.domain.usecase.FetchAllAgendasUseCase
import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetReminderUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetTaskUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveReminderUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveTaskUseCase
import com.nibalk.tasky.agenda.domain.usecase.DeleteEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.DeleteTaskUseCase
import com.nibalk.tasky.agenda.domain.usecase.DeleteReminderUseCase
import com.nibalk.tasky.agenda.presentation.detail.DetailViewModel
import com.nibalk.tasky.agenda.presentation.editor.EditorViewModel
import com.nibalk.tasky.agenda.presentation.home.HomeViewModel
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val agendaUseCaseModule = module {
    singleOf(::FormatProfileNameUseCase)
    singleOf(::FetchAllAgendasUseCase)
    singleOf(::FetchAgendasByDateUseCase)
    singleOf(::GetAgendasUseCase)
    singleOf(::GetEventUseCase)
    singleOf(::GetTaskUseCase)
    singleOf(::GetReminderUseCase)
    singleOf(::SaveTaskUseCase)
    singleOf(::SaveReminderUseCase)
    singleOf(::SaveEventUseCase)
    singleOf(::SaveTaskUseCase)
    singleOf(::SaveReminderUseCase)
    singleOf(::DeleteEventUseCase)
    singleOf(::DeleteTaskUseCase)
    singleOf(::DeleteReminderUseCase)
}

val agendaViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { parameters ->
        DetailViewModel(
            agendaArgs = parameters[0],
            savedStateHandle = parameters[1],
            getEventUseCase = get(),
            getTaskUseCase = get(),
            getReminderUseCase = get(),
            saveEventUseCase = get(),
            saveTaskUseCase = get(),
            saveReminderUseCase = get()
        )
    }
    viewModel {
        (args: EditorArgs) -> EditorViewModel(args)
    }
}
