package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import java.time.LocalDate
import java.time.LocalDateTime

class AgendaRepositoryFake() : AgendaRepository {

    override suspend fun getAgendas(
        selectedDate: LocalDate
    ): Result<List<AgendaItem>, DataError.Network> {
        val dummyData = if (selectedDate.dayOfMonth % 2 == 0) {
            generateDummyDataEven()
        } else {
            generateDummyDataOdd()
        }
        return Result.Success(dummyData)
    }

    private fun generateDummyDataEven(): List<AgendaItem> {
        return listOf(
            AgendaItem.Task(
                id = "",
                title = "Tasky Project - Complete Auth Feature 2",
                description = "Completing the Auth feature including the Login and Register flows",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                isDone = true
            ),
            AgendaItem.Event(
                id = "",
                title = "Tasky Event - Weekly call 2",
                description = "Bi-weekly call",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                endAt = LocalDateTime.now(),
                hostId = "",
                isHost = false,
            ),
            AgendaItem.Reminder(
                id = "",
                title = "Tasky Reminder 2",
                description = "Reminder to implement offline first feature",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
            ),
            AgendaItem.Task(
                id = "",
                title = "Tasky Project - Complete Auth Feature 4",
                description = "Completing the Auth feature including the Login and Register flows",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                isDone = true
            ),
            AgendaItem.Event(
                id = "",
                title = "Tasky Event - Weekly call 4",
                description = "Bi-weekly call 02",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                endAt = LocalDateTime.now(),
                hostId = "",
                isHost = false,
            ),
            AgendaItem.Reminder(
                id = "",
                title = "Tasky Reminder 4",
                description = "Reminder to implement offline first feature",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
            ),
        )
    }

    private fun generateDummyDataOdd(): List<AgendaItem> {
        return listOf(
            AgendaItem.Reminder(
                id = "",
                title = "Tasky Reminder 1",
                description = "Reminder to implement offline first feature",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
            ),
            AgendaItem.Event(
                id = "",
                title = "Tasky Event - Weekly call 1",
                description = "Bi-weekly call",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                endAt = LocalDateTime.now(),
                hostId = "",
                isHost = false,
            ),
            AgendaItem.Task(
                id = "",
                title = "Tasky Project - Complete Auth Feature 1",
                description = "Completing the Auth feature including the Login and Register flows",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                isDone = true
            ),
            AgendaItem.Reminder(
                id = "",
                title = "Tasky Reminder 3",
                description = "Reminder to implement offline first feature",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
            ),
        )
    }
}
