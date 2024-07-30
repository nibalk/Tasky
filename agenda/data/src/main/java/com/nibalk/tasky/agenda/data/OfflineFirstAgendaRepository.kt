package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.LocalEventDataSource
import com.nibalk.tasky.agenda.domain.source.local.LocalReminderDataSource
import com.nibalk.tasky.agenda.domain.source.local.LocalTaskDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteAgendaDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onError
import com.nibalk.tasky.core.domain.util.toLongDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class OfflineFirstAgendaRepository(
    private val remoteAgendaDataSource: RemoteAgendaDataSource,
    private val localEventDataSource: LocalEventDataSource,
    private val localTaskDataSource: LocalTaskDataSource,
    private val localReminderDataSource: LocalReminderDataSource,
    private val applicationScope: CoroutineScope
) : AgendaRepository {

    override suspend fun getAgendas(selectedDate: LocalDate): Flow<List<AgendaItem>> {
        val combinedAndSortedFlow: Flow<List<AgendaItem>> = combine(
            localEventDataSource.getEventsByDate(selectedDate),
            localTaskDataSource.getTasksByDate(selectedDate),
            localReminderDataSource.getRemindersByDate(selectedDate)
        ) { events, tasks, reminders ->
            Timber.d("[OfflineFirst-GetAll] LOCAL | events = %s", events)
            Timber.d("[OfflineFirst-GetAll] LOCAL | tasks = %s", tasks)
            Timber.d("[OfflineFirst-GetAll] LOCAL | reminders = %s", reminders)
            (events + tasks + reminders).sortedBy { it.startAt }
        }
        return combinedAndSortedFlow
    }

    override suspend fun fetchAgendasByDate(
        selectedDate: LocalDate
    ): EmptyResult<DataError> {
        val remoteResult = remoteAgendaDataSource.getAgendaItems(
            time = LocalDateTime.of(
                selectedDate, LocalTime.now()
            )?.toLongDateTime() ?: LocalDateTime.now().toLongDateTime()
        )

        return when (remoteResult) {
            is Result.Success -> {
                val agendaItems  = remoteResult.data
                if (agendaItems != null) {
                    applicationScope.async {
                        localEventDataSource.upsertEvents(agendaItems.events)
                            .onError { localError -> Result.Error(localError) }
                            .asEmptyDataResult()
                        localTaskDataSource.upsertTasks(agendaItems.tasks)
                            .onError { localError -> Result.Error(localError) }
                            .asEmptyDataResult()
                        localReminderDataSource.upsertReminders(agendaItems.reminders)
                            .onError { localError -> Result.Error(localError) }
                            .asEmptyDataResult()
                    }.await()
                } else {
                    Result.Success(Unit).asEmptyDataResult()
                }
            }
            is Result.Error -> {
                Result.Error(remoteResult.error)
            }
        }
    }

    override suspend fun fetchAllAgendas(): EmptyResult<DataError> {
        return when (val remoteResult = remoteAgendaDataSource.getFullAgenda()) {
            is Result.Success -> {
                val agendaItems  = remoteResult.data
                if (agendaItems != null) {
                    applicationScope.async {
                        localEventDataSource.upsertEvents(agendaItems.events)
                            .onError { localError -> Result.Error(localError) }
                            .asEmptyDataResult()
                        localTaskDataSource.upsertTasks(agendaItems.tasks)
                            .onError { localError -> Result.Error(localError) }
                            .asEmptyDataResult()
                        localReminderDataSource.upsertReminders(agendaItems.reminders)
                            .onError { localError -> Result.Error(localError) }
                            .asEmptyDataResult()
                    }.await()
                } else {
                    Result.Success(Unit).asEmptyDataResult()
                }
            }
            is Result.Error -> {
                Result.Error(remoteResult.error)
            }
        }
    }

}
