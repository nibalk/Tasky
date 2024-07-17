package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.agenda.presentation.model.NotificationDurationType
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenu
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenuItem
import com.nibalk.tasky.core.presentation.components.TaskyEditableTextRow
import com.nibalk.tasky.core.presentation.components.TaskyEditableTextRowType
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun AgendaNotificationsRow(
    modifier: Modifier = Modifier,
    currentType: NotificationDurationType = NotificationDurationType.THIRTY_MINUTES,
    isEditable: Boolean,
    onMenuItemClicked: (NotificationDurationType) -> Unit,
) {
    var isNotificationMenuShown by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.SUBTITLE,
            content = currentType.label.asString(),
            isEditable = isEditable,
            onClick = {
                isNotificationMenuShown = isEditable
            }
        )
        TaskyDropDownMenu(
            isMenuShown = isNotificationMenuShown,
            onDismissRequest = { isNotificationMenuShown = false },
            menuItems = NotificationDurationType.entries.toList().map { type->
                TaskyDropDownMenuItem(
                    displayText = type.label.asString(),
                    onClick = {
                        onMenuItemClicked(type)
                        isNotificationMenuShown = false
                    }
                )
            }
        )
    }
}

@Preview
@Composable
private fun AgendaNotificationsRowPreview() {
    TaskyTheme {
        AgendaNotificationsRow(
            isEditable = true,
            onMenuItemClicked = {}
        )
    }
}
