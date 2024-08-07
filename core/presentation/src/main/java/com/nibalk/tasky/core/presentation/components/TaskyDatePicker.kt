@file:OptIn(ExperimentalMaterial3Api::class)

package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGreen
import com.nibalk.tasky.core.presentation.themes.TaskyLightGreen
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.TaskyWhite
import com.nibalk.tasky.core.presentation.themes.spacing
import com.nibalk.tasky.core.domain.util.toLocalDate
import java.time.LocalDate
import java.time.ZoneOffset

@Composable
fun TaskyDatePicker(
    selectedDate: LocalDate,
    onCancelPicker: () -> Unit,
    onConfirmPicker: (LocalDate) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate
            .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    )

    BasicAlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
        modifier = Modifier
            .width(LocalConfiguration.current.screenWidthDp.dp),
        onDismissRequest = onCancelPicker,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colorScheme.primary),
            ) {
                DatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    state = datePickerState,
                    title = {
                        Box(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.secondary)
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .padding(MaterialTheme.spacing.spaceExtraSmall),
                        ) {
                            IconButton(onClick = onCancelPicker) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = "close",
                                    tint = TaskyWhite
                                )
                            }
                        }
                    },
                    colors = DatePickerDefaults.colors(
                        todayContentColor = TaskyDarkGreen,
                        todayDateBorderColor = TaskyDarkGreen,
                        currentYearContentColor = TaskyDarkGreen,
                        selectedYearContainerColor = TaskyLightGreen,
                        selectedDayContainerColor = TaskyLightGreen,
                    ),
                )
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.secondary)
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.spaceSmall),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            if (datePickerState.selectedDateMillis != null) {
                                onConfirmPicker(datePickerState.selectedDateMillis!!.toLocalDate())
                            }
                            onCancelPicker()
                        }
                    ) {
                        Text(text = stringResource(id = R.string.button_confirm))
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun TaskyDatePickerPreview() {
    TaskyTheme {
        TaskyDatePicker(
            LocalDate.now(), {}, {}
        )
    }
}
