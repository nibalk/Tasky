package com.nibalk.tasky

import android.app.Application
import com.nibalk.tasky.agenda.data.di.agendaDataModule
import com.nibalk.tasky.agenda.presentation.di.agendaUseCaseModule
import com.nibalk.tasky.agenda.presentation.di.agendaViewModelModule
import com.nibalk.tasky.auth.data.di.authDataModule
import com.nibalk.tasky.auth.presentation.di.authUseCaseModule
import com.nibalk.tasky.auth.presentation.di.authViewModelModule
import com.nibalk.tasky.core.data.di.coreDataModule
import com.nibalk.tasky.di.appModule
import com.nibalk.tasky.utils.TaskyTimberDebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class TaskyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(TaskyTimberDebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@TaskyApp)
            modules(
                appModule,
                authDataModule,
                authUseCaseModule,
                authViewModelModule,
                agendaDataModule,
                agendaUseCaseModule,
                agendaViewModelModule,
                coreDataModule
            )
        }
    }
}
