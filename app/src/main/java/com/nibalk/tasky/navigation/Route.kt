package com.nibalk.tasky.navigation

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

// Auth

@Serializable
object AuthNavigationGraph

@Serializable
object AuthLoginScreen

@Serializable
object AuthRegisterScreen

// Agenda

@Serializable
object AgendaNavigationGraph

@Serializable
object AgendaHomeScreen

@Serializable
data class AgendaEventScreen(
    val isEditable: Boolean,
    val agendaId: String?,
    val selectedDate: Long,
)

@Serializable
data class AgendaTaskScreen(
    val isEditable: Boolean,
    val agendaId: String?,
    val selectedDate: Long,
)

@Serializable
data class AgendaReminderScreen(
    val isEditable: Boolean,
    val agendaId: String?,
    val selectedDate: Long,
)
