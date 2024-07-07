package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenu
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenuItem
import com.nibalk.tasky.core.presentation.themes.TaskyTheme


@Composable
fun AgendaAddButton(
    onMenuItemClicked: (AgendaType) -> Unit
) {
    var isMenuShown by remember {
        mutableStateOf(false)
    }

    FloatingActionButton(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        onClick = { isMenuShown = true }
    ) {
        Box {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                contentDescription = "Add"
            )
            TaskyDropDownMenu(
                isMenuShown = isMenuShown,
                onDismissRequest = { isMenuShown = false },
                menuItems = listOf(
                    TaskyDropDownMenuItem(
                        displayText = stringResource(id = AgendaType.EVENT.menuNameResId),
                        onClick = {
                            onMenuItemClicked(AgendaType.EVENT)
                            isMenuShown = false
                        }
                    ),
                    TaskyDropDownMenuItem(
                        displayText = stringResource(id = AgendaType.TASK.menuNameResId),
                        onClick = {
                            onMenuItemClicked(AgendaType.TASK)
                            isMenuShown = false
                        }
                    ),
                    TaskyDropDownMenuItem(
                        displayText = stringResource(id = AgendaType.REMINDER.menuNameResId),
                        onClick = {
                            onMenuItemClicked(AgendaType.REMINDER)
                            isMenuShown = false
                        }
                    ),
                )
            )
        }
    }
}

@Preview
@Composable
private fun AgendaAddButtonPreview() {
    TaskyTheme {
        AgendaAddButton {}
    }
}
