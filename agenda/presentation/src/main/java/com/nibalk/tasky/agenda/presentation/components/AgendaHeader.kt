package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.core.presentation.components.TaskyHeader
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import java.time.LocalDate

@Composable
fun AgendaHeader(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    userInitials: String,
    onDayClicked: (LocalDate) -> Unit,
    onLogoutClicked: () -> Unit,
) {
    var isDatePickerShown by remember {
        mutableStateOf(false)
    }

    if (isDatePickerShown) {
        AgendaDatePicker(
            selectedDate = selectedDate,
            onCancelPicker = {
                isDatePickerShown = false
            },
            onConfirmPicker = { selectedPickerDate ->
                onDayClicked(selectedPickerDate)
            }
        )
    }

    TaskyHeader(
        modifier = modifier
    ) {
        AgendaMonthPicker(
            selectedDate = selectedDate,
            onMonthClick = {
                isDatePickerShown = true
            }
        )
        AgendaHeaderProfileIcon(
            userInitials = userInitials,
            onLogoutClicked = onLogoutClicked
        )
    }
}

@Preview
@Composable
private fun AgendaHeaderPreview() {
    TaskyTheme {
        AgendaHeader(
            selectedDate = LocalDate.now(),
            userInitials = "NI",
            onDayClicked = {},
            onLogoutClicked = {}
        )
    }
}
