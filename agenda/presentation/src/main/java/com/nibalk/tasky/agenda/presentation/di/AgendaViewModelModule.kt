package com.nibalk.tasky.agenda.presentation.di

import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.agenda.presentation.detail.DetailViewModel
import com.nibalk.tasky.agenda.presentation.editor.EditorViewModel
import com.nibalk.tasky.agenda.presentation.home.HomeViewModel
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import timber.log.Timber

val agendaUseCaseModule = module {
    singleOf(::FormatProfileNameUseCase)
    singleOf(::GetAgendasUseCase)
}

val agendaViewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { parameters ->
        Timber.d(" [NavIssueLogs] parameters.agendaArgs = %s", parameters[0])
        Timber.d(" [NavIssueLogs] parameters.savedStateHandle = %s", parameters[1])

        DetailViewModel(
            agendaArgs = parameters[0],
            savedStateHandle = parameters[1],
        )
    }
    viewModel {
        (args: EditorArgs) -> EditorViewModel(args)
    }
}
