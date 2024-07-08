package com.nibalk.tasky.agenda.presentation.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyBrownLight
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyGreen
import com.nibalk.tasky.core.presentation.themes.TaskyLightGreen
import com.nibalk.tasky.core.presentation.themes.TaskyWhite

enum class AgendaType(
    @StringRes val menuNameResId: Int,
    val backgroundColor: Color,
    val contentColor: Color,
) {
    EVENT(R.string.agenda_option_event, TaskyLightGreen, TaskyDarkGray),
    TASK(R.string.agenda_option_task, TaskyGreen, TaskyWhite),
    REMINDER(R.string.agenda_option_reminder, TaskyBrownLight, TaskyDarkGray)
}
