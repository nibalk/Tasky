package com.nibalk.tasky.agenda.data.di

import androidx.room.Room
import com.nibalk.tasky.agenda.data.OfflineFirstAgendaRepository
import com.nibalk.tasky.agenda.data.OfflineFirstEventRepository
import com.nibalk.tasky.agenda.data.OfflineFirstReminderRepository
import com.nibalk.tasky.agenda.data.OfflineFirstTaskRepository
import com.nibalk.tasky.agenda.data.local.database.AgendaDatabase
import com.nibalk.tasky.agenda.data.local.source.RoomLocalAgendaDataSource
import com.nibalk.tasky.agenda.data.local.source.RoomLocalEventDataSource
import com.nibalk.tasky.agenda.data.local.source.RoomLocalReminderDataSource
import com.nibalk.tasky.agenda.data.local.source.RoomLocalTaskDataSource
import com.nibalk.tasky.agenda.data.remote.api.AgendaApi
import com.nibalk.tasky.agenda.data.remote.api.EventApi
import com.nibalk.tasky.agenda.data.remote.api.ReminderApi
import com.nibalk.tasky.agenda.data.remote.api.TaskApi
import com.nibalk.tasky.agenda.data.remote.source.RetrofitRemoteAgendaDataSource
import com.nibalk.tasky.agenda.data.remote.source.RetrofitRemoteEventDataSource
import com.nibalk.tasky.agenda.data.remote.source.RetrofitRemoteReminderDataSource
import com.nibalk.tasky.agenda.data.remote.source.RetrofitRemoteTaskDataSource
import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.EventRepository
import com.nibalk.tasky.agenda.domain.ReminderRepository
import com.nibalk.tasky.agenda.domain.TaskRepository
import com.nibalk.tasky.agenda.domain.source.local.LocalAgendaDataSource
import com.nibalk.tasky.agenda.domain.source.local.LocalEventDataSource
import com.nibalk.tasky.agenda.domain.source.local.LocalReminderDataSource
import com.nibalk.tasky.agenda.domain.source.local.LocalTaskDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteAgendaDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteEventDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteReminderDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteTaskDataSource
import com.nibalk.tasky.core.data.networking.RetrofitFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {
    // Repo
    singleOf(::OfflineFirstAgendaRepository).bind<AgendaRepository>()
    singleOf(::OfflineFirstEventRepository).bind<EventRepository>()
    singleOf(::OfflineFirstTaskRepository).bind<TaskRepository>()
    singleOf(::OfflineFirstReminderRepository).bind<ReminderRepository>()

    // Local - Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AgendaDatabase::class.java,
            "agenda.db"
        ).build()
    }

    // Local - Dao
    single { get<AgendaDatabase>().agendaDao }
    single { get<AgendaDatabase>().eventDao }
    single { get<AgendaDatabase>().taskDao }
    single { get<AgendaDatabase>().reminderDao }

    // Local - Data Sources
    singleOf(::RoomLocalAgendaDataSource).bind<LocalAgendaDataSource>()
    singleOf(::RoomLocalEventDataSource).bind<LocalEventDataSource>()
    singleOf(::RoomLocalTaskDataSource).bind<LocalTaskDataSource>()
    singleOf(::RoomLocalReminderDataSource).bind<LocalReminderDataSource>()


    // Remote - Api
    single<AgendaApi> {
        get<RetrofitFactory>().build().create(AgendaApi::class.java)
    }
    single<EventApi> {
        get<RetrofitFactory>().build().create(EventApi::class.java)
    }
    single<TaskApi> {
        get<RetrofitFactory>().build().create(TaskApi::class.java)
    }
    single<ReminderApi> {
        get<RetrofitFactory>().build().create(ReminderApi::class.java)
    }

    // Remote - Data Sources
    singleOf(::RetrofitRemoteAgendaDataSource).bind<RemoteAgendaDataSource>()
    singleOf(::RetrofitRemoteEventDataSource).bind<RemoteEventDataSource>()
    singleOf(::RetrofitRemoteTaskDataSource).bind<RemoteTaskDataSource>()
    singleOf(::RetrofitRemoteReminderDataSource).bind<RemoteReminderDataSource>()
}

