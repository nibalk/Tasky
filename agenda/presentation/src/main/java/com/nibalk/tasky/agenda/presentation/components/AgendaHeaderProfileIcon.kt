package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenu
import com.nibalk.tasky.core.presentation.components.TaskyDropDownMenuItem
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import java.time.LocalDate

@Composable
fun AgendaHeaderProfileIcon(
    userInitials: String,
    onLogoutClicked: () -> Unit,
) {
    var isLogoutMenuShown by remember {
        mutableStateOf(false)
    }

    Box {
        AgendaInitialsIcon(
            userInitials = userInitials,
            onIconClicked = {
                isLogoutMenuShown = true
            }
        )
        TaskyDropDownMenu(
            isMenuShown = isLogoutMenuShown,
            onDismissRequest = { isLogoutMenuShown = false },
            menuItems = listOf(
                TaskyDropDownMenuItem(
                    displayText = "Logout",
                    onClick = {
                        onLogoutClicked()
                        isLogoutMenuShown = false
                    }
                )
            )
        )
    }
}

@Preview
@Composable
private fun AgendaHeaderPreview() {
    TaskyTheme {
        AgendaHeader(
            selectedDate = LocalDate.now(),
            userInitials = "AB",
            onDayClicked = {},
            onLogoutClicked = {}
        )
    }
}
