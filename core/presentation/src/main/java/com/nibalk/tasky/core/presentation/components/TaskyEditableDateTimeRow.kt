package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TaskyEditableDateTimeRow(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    datePattern: String = "MMM dd yyyy",
    timePattern: String = "HH:mm",
    selectedDateTime: LocalDateTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    label: String,
    isEditable: Boolean,
) {

    var isDatePickerShown by remember { mutableStateOf(false) }
    var isTimePickerShown by remember { mutableStateOf(false) }

    if (isDatePickerShown) {
        TaskyDatePicker(
            selectedDate = selectedDateTime.toLocalDate(),
            onCancelPicker = {
                isDatePickerShown = false
            },
            onConfirmPicker = { selectedPickerDate ->
                onDateSelected(selectedPickerDate)
            }
        )
    }
    if (isTimePickerShown) {
        TaskyTimePicker(
            selectedTime = selectedDateTime.toLocalTime(),
            onCancelPicker = {
                isTimePickerShown = false
            },
            onConfirmPicker = { selectedPickerTime ->
                onTimeSelected(selectedPickerTime)
            }
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(4f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = contentColor,
            )
            // Selected Time
            Row(
                modifier = Modifier
                    .then(
                        if (isEditable) {
                            Modifier.clickable { isTimePickerShown = true }
                        } else Modifier
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp),
                    text = selectedDateTime.format(DateTimeFormatter.ofPattern(timePattern)),
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor,
                )
                if (isEditable) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(R.string.content_description_edit),
                        tint = contentColor,
                    )
                }
            }
        }
        // Selected Date
        Row(
            modifier = Modifier
                .weight(4f)
                .then(
                    if (isEditable) {
                        Modifier.clickable { isDatePickerShown = true }
                    } else Modifier
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp),
                text = selectedDateTime.format(DateTimeFormatter.ofPattern(datePattern)),
                style = MaterialTheme.typography.labelMedium,
                color = contentColor,
            )
            if (isEditable) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.content_description_edit),
                    tint = contentColor,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TaskyEditableDateTimeRowPreview() {
    TaskyTheme {
        TaskyEditableDateTimeRow(
            selectedDateTime = LocalDateTime.now(),
            onTimeSelected = {},
            onDateSelected = {},
            label = "From",
            isEditable = true,
        )
    }
}

