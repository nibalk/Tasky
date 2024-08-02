package com.nibalk.tasky.agenda.presentation.di

import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveReminderUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveTaskUseCase
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
    singleOf(::SaveTaskUseCase)
    singleOf(::SaveReminderUseCase)
    singleOf(::SaveEventUseCase)
}

val agendaViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { parameters ->
        DetailViewModel(
            savedStateHandle = parameters[0],
            agendaArgs = parameters[1],
            saveEventUseCase = get(),
            saveTaskUseCase = get(),
            saveReminderUseCase = get(),
            eventRepository = get(),
            taskRepository = get(),
            reminderRepository = get(),
        )
    }
    viewModel {
        (args: EditorArgs) -> EditorViewModel(args)
    }
}
