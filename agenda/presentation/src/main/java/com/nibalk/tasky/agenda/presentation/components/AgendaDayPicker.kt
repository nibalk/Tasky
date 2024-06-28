package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyOrange
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import java.time.LocalDate


@Composable
fun AgendaDayPicker(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    selectedDay: Int = 0,
    onDayClick: (Int) -> Unit,
) {
    val itemsList = (0..< 6).map {
        selectedDate.plusDays(it.toLong())
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        itemsIndexed(itemsList) { index, date ->
            AgendaDayPickerItem(
                selectedDate = date,
                isSelected = selectedDay == index,
                onDayClick = { onDayClick(index) }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AgendaPickerPreview() {
    TaskyTheme {
        AgendaDayPicker(
            selectedDate = LocalDate.now(),
            selectedDay = 3,
            onDayClick = {}
        )
    }
}

@Composable
private fun AgendaDayPickerItem(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    isSelected: Boolean = false,
    onDayClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(if (isSelected) TaskyOrange else Color.Transparent)
            .clickable { onDayClick() }
            .padding(MaterialTheme.spacing.spaceSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = selectedDate.dayOfWeek.name[0].toString(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) TaskyDarkGray else TaskyGray
        )
        Text(
            text = selectedDate.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = TaskyDarkGray
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AgendaDayPickerItemPreview() {
    TaskyTheme {
        AgendaDayPickerItem(
            selectedDate = LocalDate.now(),
            isSelected = true,
            onDayClick = {}
        )
    }
}
