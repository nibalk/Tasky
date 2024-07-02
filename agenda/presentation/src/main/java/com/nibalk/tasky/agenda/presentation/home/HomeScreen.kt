package com.nibalk.tasky.agenda.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
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
    // Simulated API call
    val items = remember {
        (46..60).map { "Item $it" }.toMutableList()
    }
    var nextIndex = 45

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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AgendaDayPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(80.dp),
                selectedDate = LocalDate.now(),
                onDayClick = {}
            )
            AgendaRefreshableList(
                items = items,
                content = { itemTitle ->
                    Text(text = itemTitle)
                },
                isRefreshing = isRefreshing,
                onRefresh = {
                    coroutineScope.launch {
                        isRefreshing = true
                        // Simulated API call
                        delay(1000L)
                        if (nextIndex > 0) {
                            items.addAll(
                                0,
                                (nextIndex downTo (nextIndex - 14))
                                    .map { "Item $it" }.reversed()
                            )
                        }
                        nextIndex -= 15
                        isRefreshing = false
                    }
                }
            )
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

@PreviewScreenSizes
@Composable
private fun HomeScreenPreviewScreenSizes() {
    TaskyTheme {
        HomeScreen()
    }
}
