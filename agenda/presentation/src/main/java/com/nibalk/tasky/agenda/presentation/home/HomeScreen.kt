package com.nibalk.tasky.agenda.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.agenda.presentation.components.AgendaDayPicker
import com.nibalk.tasky.agenda.presentation.components.AgendaHeader
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import java.time.LocalDate

@Composable
fun HomeScreen(

) {
    TaskyBackground(
        header = {
            AgendaHeader(
                selectedDate = LocalDate.now(),
                userInitials = "NB",
                onMonthPickerClick = { },
                onProfileIconClick = { }
            )
        }
    ) {
        AgendaDayPicker(
            selectedDate = LocalDate.now(),
            onDayClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        HomeScreen()
    }
}
