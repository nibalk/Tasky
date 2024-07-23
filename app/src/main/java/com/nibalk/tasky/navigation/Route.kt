package com.nibalk.tasky.navigation

import com.nibalk.tasky.agenda.presentation.model.AgendaType
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
data class AgendaDetailScreen(
    val isEditable: Boolean,
    val selectedDate: Long,
    val agendaType: String,
    val agendaId: String?,
)

@Serializable
data class AgendaEditorScreen(
    val editorText: String?,
    val editorType: String,
    val agendaType: String,
)
