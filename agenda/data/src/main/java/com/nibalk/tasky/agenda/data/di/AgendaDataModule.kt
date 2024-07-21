package com.nibalk.tasky.agenda.data.di

import androidx.room.Room
import com.nibalk.tasky.agenda.data.AgendaRepositoryFake
import com.nibalk.tasky.agenda.data.local.database.AgendaDatabase
import com.nibalk.tasky.agenda.domain.AgendaRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    // Repo
    singleOf(::AgendaRepositoryFake).bind<AgendaRepository>()

    // Local Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AgendaDatabase::class.java,
            "agenda.db"
        ).build()
    }
    single { get<AgendaDatabase>().eventDao }
    single { get<AgendaDatabase>().taskDao }
    single { get<AgendaDatabase>().reminderDao }
}

