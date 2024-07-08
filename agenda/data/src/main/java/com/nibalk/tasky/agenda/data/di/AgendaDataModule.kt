package com.nibalk.tasky.agenda.data.di

import com.nibalk.tasky.agenda.data.AgendaRepositoryFake
import com.nibalk.tasky.agenda.domain.AgendaRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    singleOf(::AgendaRepositoryFake).bind<AgendaRepository>()
}
