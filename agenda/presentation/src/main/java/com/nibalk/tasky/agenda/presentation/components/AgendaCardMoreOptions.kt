package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.agenda.presentation.model.AgendaItemActionType
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenu
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenuItem
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun AgendaCardMoreOptions(
    modifier: Modifier = Modifier,
    contentColor: Color,
    onMenuItemClicked: (AgendaItemActionType) -> Unit,
) {
    var isMoreMenuShown by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                isMoreMenuShown = true
            },
        ) {
            Icon(
                modifier = Modifier.rotate(90.0f),
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "options",
                tint = contentColor
            )
        }
        TaskyDropDownMenu(
            isMenuShown = isMoreMenuShown,
            onDismissRequest = { isMoreMenuShown = false },
            menuItems = listOf(
                TaskyDropDownMenuItem(
                    displayText = stringResource(id = AgendaItemActionType.OPEN.menuNameResId),
                    leadingIcon = AgendaItemActionType.OPEN.menuIconResId,
                    onClick = {
                        onMenuItemClicked(AgendaItemActionType.OPEN)
                        isMoreMenuShown = false
                    }
                ),
                TaskyDropDownMenuItem(
                    displayText = stringResource(id = AgendaItemActionType.EDIT.menuNameResId),
                    leadingIcon = AgendaItemActionType.EDIT.menuIconResId,
                    onClick = {
                        onMenuItemClicked(AgendaItemActionType.EDIT)
                        isMoreMenuShown = false
                    }
                ),
                TaskyDropDownMenuItem(
                    displayText = stringResource(id = AgendaItemActionType.DELETE.menuNameResId),
                    leadingIcon = AgendaItemActionType.DELETE.menuIconResId,
                    onClick = {
                        onMenuItemClicked(AgendaItemActionType.DELETE)
                        isMoreMenuShown = false
                    }
                ),
            )
        )
    }
}

@Preview
@Composable
private fun AgendaCardMoreOptionsPreview() {
    TaskyTheme {
        AgendaCardMoreOptions(
            contentColor = TaskyDarkGray,
            onMenuItemClicked = {}
        )
    }
}
