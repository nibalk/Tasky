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
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.agenda.presentation.components.AgendaDetailHeader
import com.nibalk.tasky.agenda.presentation.components.AgendaFooter
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
import java.time.LocalTime

@Composable
fun DetailScreenRoot(
    agendaArgs: AgendaArgs,
    viewModel: DetailViewModel = koinViewModel { parametersOf(agendaArgs) },
) {
    DetailScreen(
        state = viewModel.state,
        onAction = { action ->
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
                    id = R.string.agenda_item_edit, state.agendaType?.name.orEmpty()
                ).uppercase(),
                onCloseDetail = { },
                onSaveDetail = { }
            ) {

            }
        },
        footer = {
            if (WindowInsets.ime.getBottom(LocalDensity.current) <= 0) {
                AgendaFooter(
                    content = stringResource(
                        id = R.string.agenda_item_delete, state.agendaType?.name.orEmpty()
                    ).uppercase(),
                    onButtonClicked = {}
                )
            }
        }
    ) {
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.TITLE,
            content = state.agendaItem?.title.orEmpty(),
            hint = stringResource(id = R.string.agenda_item_enter_title),
            isEditable = state.isEditingMode,
            onClick = {}
        )
        HorizontalDivider(color = TaskyLightBlue)
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.DESCRIPTION,
            content = state.agendaItem?.description.orEmpty(),
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
            selectedDateTime = state.agendaItem?.startAt
                ?: state.selectedDate.atTime(LocalTime.now()),
            onTimeSelected = { },
            onDateSelected = { }
        )
        HorizontalDivider(color = TaskyLightBlue)
        if (state.agendaType == AgendaType.EVENT) {
            TaskyEditableDateTimeRow(
                label = stringResource(R.string.agenda_item_to),
                isEditable = state.isEditingMode,
                selectedDateTime = if (state.agendaItem is AgendaItem.Event) {
                    state.agendaItem.endAt
                } else state.selectedDate.atTime(LocalTime.now()),
                onTimeSelected = { },
                onDateSelected = { }
            )
            HorizontalDivider(color = TaskyLightBlue)
        }
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
                agendaId = AgendaSampleData.event1.id,
                agendaItem = AgendaSampleData.event1,
            ),
            onAction = {},
        )
    }
}
