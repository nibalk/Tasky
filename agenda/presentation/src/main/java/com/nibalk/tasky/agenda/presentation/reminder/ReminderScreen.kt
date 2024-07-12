package com.nibalk.tasky.agenda.presentation.reminder

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.agenda.presentation.components.AgendaDetailHeader
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import java.time.LocalDate

@Composable
fun ReminderScreenRoot(
    agendaArgs: AgendaArgs
) {
    ReminderScreen(
        isEditable = agendaArgs.isEditable,
        agendaId = agendaArgs.agendaId,
        selectedDate = agendaArgs.selectedDate ?: LocalDate.now()
    )
}

@Composable
fun ReminderScreen(
    isEditable: Boolean,
    agendaId: String?,
    selectedDate: LocalDate,
) {
    TaskyBackground(
        header = {
            AgendaDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                isEditable = isEditable,
                headerDate = selectedDate,
                headerTitle = stringResource(
                    id = R.string.agenda_item_edit, AgendaType.REMINDER
                ).uppercase(),
                onCloseDetail = { },
                onSaveDetail = { }
            ) {

            }
        }
    ) {

    }

}
