package com.nibalk.tasky.navigation

import kotlinx.serialization.Serializable

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
    val agendaId: String?
)

@Serializable
data class AgendaTaskScreen(
    val isEditable: Boolean,
    val agendaId: String?
)

@Serializable
data class AgendaReminderScreen(
    val isEditable: Boolean,
    val agendaId: String?
)
