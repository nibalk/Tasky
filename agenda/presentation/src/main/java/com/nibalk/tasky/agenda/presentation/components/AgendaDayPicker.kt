package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.core.presentation.utils.getSurroundingDays
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyDarkOrange
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyOrange
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import java.time.LocalDate


@Composable
fun AgendaDayPicker(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    datesList: List<LocalDate>,
    onDayClicked: (LocalDate) -> Unit,
) {
    val selectedIndex = datesList.indexOf(selectedDate)
    val lazyRowState = rememberLazyListState(
        initialFirstVisibleItemIndex = selectedIndex
    )

    LaunchedEffect(selectedDate) {
        if (selectedIndex >= 0) {
            lazyRowState.animateScrollToItem(selectedIndex)
        }
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceLarge),
        state = lazyRowState
    ) {
        itemsIndexed(datesList) { _, date ->
            AgendaDayPickerItem(
                selectedDate = date,
                isSelected = date == selectedDate,
                onDayClick = {
                    onDayClicked(date)
                }
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
            datesList = LocalDate.now().getSurroundingDays(),
            onDayClicked = {}
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
            .widthIn(MaterialTheme.spacing.spaceSmall)
            .fillMaxHeight()
            .clip(RoundedCornerShape(100))
            .background(if (isSelected) TaskyOrange else Color.Transparent)
            .clickable {
                onDayClick()
            }
            .border(
                width = 1.dp,
                color = if (selectedDate == LocalDate.now() && !isSelected) {
                    TaskyDarkOrange
                } else Color.Transparent,
                shape = RoundedCornerShape(100)
            )
            .padding(
                horizontal = MaterialTheme.spacing.spaceSmall,
                vertical = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ){
        Text(
            text = selectedDate.dayOfWeek.name[0].toString(),
            fontSize = 13.sp,
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
