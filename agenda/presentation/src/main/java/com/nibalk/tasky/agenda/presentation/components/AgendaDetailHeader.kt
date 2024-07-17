package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.core.presentation.components.TaskyHeader
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AgendaDetailHeader(
    modifier: Modifier = Modifier,
    datePattern: String = "dd MMMM yyyy",
    isEditable: Boolean,
    headerDate: LocalDate,
    headerTitle: String,
    onCloseDetail: () -> Unit,
    onSaveDetail: () -> Unit,
    onIsEditableChanged: (isEditable: Boolean) -> Unit,
) {
    TaskyHeader(
        modifier = modifier
            .padding(horizontal = 0.dp)
    ) {
        IconButton(
            onClick = { onCloseDetail() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                tint = MaterialTheme.colorScheme.onSecondary,
                contentDescription = stringResource(R.string.content_description_close)
            )
        }
        Text(
            text = if (isEditable) {
                headerTitle.uppercase()
            } else {
                headerDate.format(DateTimeFormatter.ofPattern(datePattern)).uppercase()
            },
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        if (isEditable) {
            Text(
                modifier = Modifier
                    .clickable {
                        onIsEditableChanged(false)
                        onSaveDetail()
                    },
                text = stringResource(id = R.string.agenda_item_save),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        } else {
            IconButton(
                onClick = {
                    onIsEditableChanged(true)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    contentDescription = stringResource(R.string.content_description_edit)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AgendaDetailHeaderEditPreview() {
    val isEditing = remember {
        mutableStateOf(false)
    }
    TaskyTheme {
        AgendaDetailHeader(
            headerDate = LocalDate.now(),
            headerTitle = "Edit Reminder",
            isEditable = isEditing.value,
            onCloseDetail = {},
            onSaveDetail = {},
            onIsEditableChanged = {}
        )
    }
}

@Preview
@Composable
private fun AgendaDetailHeaderSavePreview() {
    val isEditing = remember {
        mutableStateOf(true)
    }
    TaskyTheme {
        AgendaDetailHeader(
            headerDate = LocalDate.now(),
            headerTitle = "Edit Task",
            isEditable = isEditing.value,
            onCloseDetail = {},
            onSaveDetail = {},
            onIsEditableChanged = {}
        )
    }
}
