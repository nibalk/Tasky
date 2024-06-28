package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.core.presentation.components.TaskyHeader
import com.nibalk.tasky.core.presentation.themes.TaskyBlue
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import java.time.LocalDate

@Composable
fun AgendaHeader(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    userInitials: String,
    onMonthPickerClick: () -> Unit,
    onProfileIconClick: () -> Unit,
) {
    TaskyHeader(
        modifier = modifier
    ) {
        AgendaMonthPicker(
            currentDate = currentDate,
            onMonthClick = onMonthPickerClick
        )
        AgendaHeaderProfileIcon(
            userInitials = userInitials,
            onProfileIconClick = onProfileIconClick
        )
    }
}

@Preview
@Composable
private fun AgendaHeaderPreview() {
    AgendaHeader(
        currentDate = LocalDate.now(),
        userInitials = "AB",
        onMonthPickerClick = {},
        onProfileIconClick = {}
    )
}

@Composable
private fun AgendaHeaderProfileIcon(
    modifier: Modifier = Modifier,
    userInitials: String,
    onProfileIconClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = TaskyLightBlue, shape = CircleShape)
            .clickable { onProfileIconClick() }
            .padding(8.dp)
    ) {
        Text(
            text = userInitials,
            color = TaskyBlue,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun AgendaHeaderProfileIconPreview() {
    AgendaHeaderProfileIcon(
        userInitials = "AB",
        onProfileIconClick = {}
    )
}
