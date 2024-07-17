package com.nibalk.tasky.agenda.presentation.detail

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.agenda.presentation.components.AgendaDetailHeader
import com.nibalk.tasky.agenda.presentation.components.AgendaFooter
import com.nibalk.tasky.agenda.presentation.components.AgendaNotificationsRow
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.core.presentation.components.TaskyEditableDateTimeRow
import com.nibalk.tasky.core.presentation.components.TaskyEditableTextRow
import com.nibalk.tasky.core.presentation.components.TaskyEditableTextRowType
import com.nibalk.tasky.core.presentation.components.TaskyScrollableBackground
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.test.mock.AgendaSampleData
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDateTime

@Composable
fun DetailScreenRoot(
    onCloseClicked: () -> Unit,
    agendaArgs: AgendaArgs,
    viewModel: DetailViewModel = koinViewModel { parametersOf(agendaArgs) },
) {
    DetailScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is DetailAction.OnCloseClicked -> {
                    onCloseClicked()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun DetailScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit,
) {
    TaskyScrollableBackground(
        header = {
            AgendaDetailHeader(
                modifier = Modifier.fillMaxWidth(),
                isEditable = state.isEditingMode,
                headerDate = state.selectedDate,
                headerTitle = stringResource(
                    id = R.string.agenda_item_edit, state.agendaType.name
                ).uppercase(),
                onCloseDetail = {
                    onAction(DetailAction.OnCloseClicked)
                },
                onSaveDetail = {
                    onAction(DetailAction.OnSaveClicked)
                },
                onIsEditableChanged = { isEditable ->
                    onAction(DetailAction.OnIsEditableChanged(isEditable))
                }
            )
        },
        footer = {
            if (WindowInsets.ime.getBottom(LocalDensity.current) <= 0) {
                AgendaFooter(
                    content = stringResource(
                        id = R.string.agenda_item_delete, state.agendaType.name
                    ).uppercase(),
                    onButtonClicked = {}
                )
            }
        }
    ) {
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.TITLE,
            content = state.title,
            hint = stringResource(id = R.string.agenda_item_enter_title),
            isEditable = state.isEditingMode,
            onClick = {}
        )
        HorizontalDivider(color = TaskyLightBlue)
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.DESCRIPTION,
            content = state.description,
            hint = stringResource(id = R.string.agenda_item_enter_description),
            isEditable = state.isEditingMode,
            onClick = {}
        )
        HorizontalDivider(color = TaskyLightBlue)
        TaskyEditableDateTimeRow(
            label = stringResource(
                if (state.agendaType == AgendaType.EVENT) {
                    R.string.agenda_item_from
                } else R.string.agenda_item_at
            ),
            isEditable = state.isEditingMode,
            selectedDateTime = state.startDate.atTime(state.startTime),
            onTimeSelected = { selectedTime ->
                onAction(DetailAction.OnStartTimeSelected(selectedTime))
            },
            onDateSelected = { selectedDate ->
                onAction(DetailAction.OnStartDateSelected(selectedDate))
            }
        )
        HorizontalDivider(color = TaskyLightBlue)
        if (state.agendaType == AgendaType.EVENT) {
            TaskyEditableDateTimeRow(
                label = stringResource(R.string.agenda_item_to),
                isEditable = state.isEditingMode,
                selectedDateTime = state.getEventDetailField(
                    state.details
                ) { event ->
                    event.endDate.atTime(event.endTime)
                } ?: LocalDateTime.now(),
                onTimeSelected = { selectedTime ->
                    onAction(DetailAction.OnEndTimeSelected(selectedTime))
                },
                onDateSelected = { selectedDate ->
                    onAction(DetailAction.OnEndDateSelected(selectedDate))
                }
            )
            HorizontalDivider(color = TaskyLightBlue)
        }
        AgendaNotificationsRow(
            isEditable = state.isEditingMode,
            currentType = state.notificationDurationType,
            onMenuItemClicked = { notificationDurationType ->
                onAction(DetailAction.OnNotificationDurationClicked(notificationDurationType))
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DetailScreenPreview() {
    TaskyTheme {
        DetailScreen(
            state = DetailState(
                isEditingMode = false,
                agendaType = AgendaType.EVENT,
                agendaId = AgendaSampleData.event2.id,
                title = AgendaSampleData.event2.title,
                description = AgendaSampleData.event2.description,
                startDate = AgendaSampleData.event2.startAt.toLocalDate(),
                startTime = AgendaSampleData.event2.startAt.toLocalTime(),
                details = AgendaItemDetails.Event(
                    endDate = AgendaSampleData.event2.endAt.toLocalDate(),
                    endTime = AgendaSampleData.event2.endAt.toLocalTime(),
                )
            ),
            onAction = {},
        )
    }
}
