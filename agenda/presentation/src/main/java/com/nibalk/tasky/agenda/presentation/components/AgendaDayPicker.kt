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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.agenda.presentation.utils.getSurroundingDays
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
    onDayClick: (Int) -> Unit,
) {
    val starIndex = 12
    val lazyRowState = rememberLazyListState(
        initialFirstVisibleItemIndex = starIndex
    )
    val itemsList = selectedDate.getSurroundingDays(before = starIndex)

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceMedium),
        state = lazyRowState
    ) {
        itemsIndexed(itemsList) { index, date ->
            AgendaDayPickerItem(
                selectedDate = date,
                isSelected = date == selectedDate,
                onDayClick = {
                    onDayClick(index)
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
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenPaddings = 128.dp // 16+16+8+8+(16*5)

    Column(
        modifier = modifier
            .width((screenWidth - screenPaddings) / 6)
            .fillMaxHeight()
            .clip(RoundedCornerShape(100))
            .background(if (isSelected) TaskyOrange else Color.Transparent)
            .clickable { onDayClick() }
            .padding(
                horizontal = MaterialTheme.spacing.spaceSmall,
                vertical = 12.dp
            )
            .border(
                width = 1.dp,
                color = if (selectedDate == LocalDate.now() && !isSelected) {
                    TaskyOrange
                } else Color.Transparent,
                shape = RoundedCornerShape(100)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ){
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
