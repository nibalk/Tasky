package com.nibalk.tasky.agenda.presentation.detail

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.nibalk.tasky.agenda.domain.model.EventPhoto
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.agenda.presentation.components.AgendaDetailHeader
import com.nibalk.tasky.agenda.presentation.components.AgendaFooter
import com.nibalk.tasky.agenda.presentation.components.AgendaNotificationsRow
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.agenda.presentation.model.EditorType
import com.nibalk.tasky.core.presentation.components.TaskyEditableDateTimeRow
import com.nibalk.tasky.core.presentation.components.TaskyEditablePhotoRow
import com.nibalk.tasky.core.presentation.components.TaskyEditableTextRow
import com.nibalk.tasky.core.presentation.components.TaskyEditableTextRowType
import com.nibalk.tasky.core.presentation.components.TaskyScrollableBackground
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import com.nibalk.tasky.core.presentation.utils.ObserveAsEvents
import com.nibalk.tasky.core.presentation.utils.getCompressedByteArray
import com.nibalk.tasky.test.mock.AgendaSampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.net.URL
import java.time.LocalDateTime

@Composable
fun DetailScreenRoot(
    onCloseClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onEditorClicked: (
        editorText: String?, EditorType
    ) -> Unit,
    agendaArgs: AgendaArgs,
    navController: NavHostController,
    viewModel: DetailViewModel = koinViewModel {
        parametersOf(navController.currentBackStackEntry?.savedStateHandle, agendaArgs)
    },
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val updatedTitle = navController.currentBackStackEntry?.savedStateHandle?.get<String>(EditorType.TITLE.name)
        val updatedDescription = navController.currentBackStackEntry?.savedStateHandle?.get<String>(EditorType.DESCRIPTION.name)
        if (updatedTitle != null) {
            viewModel.onAction(DetailAction.OnTitleEdited(updatedTitle))
        }
        if (updatedDescription != null) {
            viewModel.onAction(DetailAction.OnDescriptionEdited(updatedDescription))
        }
    }

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when(event) {
            is DetailEvent.DetailSaveError -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }
            DetailEvent.DetailSaveSuccess -> {
                Toast.makeText(context, R.string.agenda_item_saved, Toast.LENGTH_SHORT).show()
                onSaveClicked()
            }
        }
    }

    DetailScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is DetailAction.OnCloseClicked -> {
                    onCloseClicked()
                }
                is DetailAction.OnTitleClicked -> {
                    onEditorClicked(
                        viewModel.state.title,
                        EditorType.TITLE
                    )
                }
                is DetailAction.OnDescriptionClicked -> {
                    onEditorClicked(
                        viewModel.state.description,
                        EditorType.DESCRIPTION
                    )
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
    val context = LocalContext.current
    val itemModifier: Modifier = Modifier.padding(
        horizontal = MaterialTheme.spacing.spaceMedium
    )

    val compressedPhotos = remember { mutableStateListOf<ByteArray?>() }
    val compressedPhotosTemp = remember { mutableStateListOf<ByteArray?>() }

    if (state.agendaType == AgendaType.EVENT) {
        LaunchedEffect(state.details.asEventDetails.photos) {
            compressedPhotosTemp.clear()
            state.details.asEventDetails.photos.forEach { eventPhoto ->
                val compressedByteArray = when(eventPhoto) {
                    is EventPhoto.Local -> {
                        withContext(Dispatchers.IO) {
                            Uri.parse(eventPhoto.location).getCompressedByteArray(context)
                        }
                    }
                    is EventPhoto.Remote -> {
                        withContext(Dispatchers.IO) {
                            URL(eventPhoto.location).getCompressedByteArray()
                        }
                    }
                }
                compressedPhotosTemp.add(compressedByteArray)
            }
            compressedPhotos.clear()
            compressedPhotos.addAll(compressedPhotosTemp)
        }
    }

    TaskyScrollableBackground(
        contentHorizontalPadding = MaterialTheme.spacing.default,
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
                    isLoading = state.isLoading,
                    onButtonClicked = {}
                )
            }
        }
    ) {
        TaskyEditableTextRow(
            modifier = itemModifier,
            rowType = TaskyEditableTextRowType.TITLE,
            content = state.title,
            hint = stringResource(id = R.string.agenda_item_enter_title),
            isEditable = state.isEditingMode,
            onClick = { onAction(DetailAction.OnTitleClicked) }
        )
        HorizontalDivider(color = TaskyLightBlue)
        TaskyEditableTextRow(
            modifier = itemModifier,
            rowType = TaskyEditableTextRowType.DESCRIPTION,
            content = state.description,
            hint = stringResource(id = R.string.agenda_item_enter_description),
            isEditable = state.isEditingMode,
            onClick = { onAction(DetailAction.OnDescriptionClicked) }
        )
        HorizontalDivider(color = TaskyLightBlue)
        if (state.agendaType == AgendaType.EVENT) {
            TaskyEditablePhotoRow(
                photos = compressedPhotos,
                onPhotoViewed = {
                    onAction(DetailAction.OnPhotoViewed(it))
                },
                onPhotoAdded = {
                    onAction(DetailAction.OnPhotoAdded(it))
                },
                isEditable = state.isEditingMode
            )
            HorizontalDivider(color = TaskyLightBlue)
        }
        TaskyEditableDateTimeRow(
            modifier = itemModifier,
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
                modifier = itemModifier,
                label = stringResource(R.string.agenda_item_to),
                isEditable = state.isEditingMode,
                selectedDateTime = state.details.asEventDetails.endDate.atTime(
                    state.details.asEventDetails.endTime
                ) ?: LocalDateTime.now(),
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
            modifier = itemModifier,
            isEditable = state.isEditingMode,
            currentType = state.reminderDurationType,
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
                agendaId = AgendaSampleData.event2.id.orEmpty(),
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
