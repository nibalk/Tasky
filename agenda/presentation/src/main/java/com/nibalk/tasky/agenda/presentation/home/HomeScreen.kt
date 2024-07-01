package com.nibalk.tasky.agenda.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.agenda.presentation.components.AgendaDayPicker
import com.nibalk.tasky.agenda.presentation.components.AgendaHeader
import com.nibalk.tasky.agenda.presentation.components.AgendaRefreshableList
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomeScreen(

) {
    val items = remember {
        (1..100).map { "Item $it" }
    }
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
        val pickerHeight = 1.0f

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AgendaDayPicker(
                modifier = Modifier
                    .fillMaxSize()
                    .then(Modifier.weight(pickerHeight)),
                selectedDate = LocalDate.now(),
                onDayClick = {}
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(Modifier.weight(10 - pickerHeight)),
            ) {
                AgendaRefreshableList(
                    items = items,
                    content = { itemTitle ->
                        Text(
                            text = itemTitle,
                        )
                    },
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        coroutineScope.launch {
                            isRefreshing = true
                            delay(3000L) // Simulated API call
                            isRefreshing = false
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HomeScreenPreview() {
    TaskyTheme {
        HomeScreen()
    }
}
