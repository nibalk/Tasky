package com.nibalk.tasky.agenda.data.di

import androidx.room.Room
import com.nibalk.tasky.agenda.data.AgendaRepositoryFake
import com.nibalk.tasky.agenda.data.local.database.AgendaDatabase
import com.nibalk.tasky.agenda.data.local.source.RoomLocalReminderDataSource
import com.nibalk.tasky.agenda.data.local.source.RoomLocalTaskDataSource
import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.source.local.LocalReminderDataSource
import com.nibalk.tasky.agenda.domain.source.local.LocalTaskDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    // Repo
    singleOf(::AgendaRepositoryFake).bind<AgendaRepository>()

    // Local - Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AgendaDatabase::class.java,
            "agenda.db"
        ).build()
    }

    // Local - Dao
    single { get<AgendaDatabase>().eventDao }
    single { get<AgendaDatabase>().taskDao }
    single { get<AgendaDatabase>().reminderDao }

    // Local - Data Sources
    singleOf(::RoomLocalTaskDataSource).bind<LocalTaskDataSource>()
    singleOf(::RoomLocalReminderDataSource).bind<LocalReminderDataSource>()
}

