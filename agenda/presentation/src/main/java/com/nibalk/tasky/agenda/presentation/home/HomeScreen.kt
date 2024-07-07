package com.nibalk.tasky.agenda.presentation.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.nibalk.tasky.agenda.presentation.components.AgendaAddButton
import com.nibalk.tasky.agenda.presentation.components.AgendaDayPicker
import com.nibalk.tasky.agenda.presentation.components.AgendaHeader
import com.nibalk.tasky.agenda.presentation.components.AgendaRefreshableList
import com.nibalk.tasky.agenda.presentation.utils.getSurroundingDays
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.utils.UiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    onDetailClicked: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    HomeScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is HomeAction.OnAgendaListItemClicked -> onDetailClicked()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    // Simulated API call
    val items = remember {
        (46..60).map { "Item $it" }.toMutableList()
    }
    var nextIndex = 45


    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isListRefreshing by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            AgendaAddButton { agendaType ->
                Toast.makeText(
                    context,
                    "${getString(context, agendaType.menuNameResId)} clicked!",
                    Toast.LENGTH_LONG
                ).show()
                onAction(HomeAction.OnAddAgendaOptionsClicked(agendaType))
            }
        },
    ) {
        TaskyBackground(
            header = {
                AgendaHeader(
                    selectedDate = state.selectedDate,
                    userInitials = state.profileInitials,
                    onDayClicked = { clickedDate ->
                        onAction(HomeAction.OnDayClicked(clickedDate))
                    },
                    onProfileIconClicked = {
                        onAction(HomeAction.OnProfileClicked)
                    }
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
                    selectedDate = state.selectedDate,
                    datesList = state.selectedDate.getSurroundingDays(),
                    onDayClicked = { clickedDate ->
                        onAction(HomeAction.OnDayClicked(clickedDate))
                    }
                )
                AgendaRefreshableList(
                    items = items,
                    content = { itemTitle ->
                        Text(text = itemTitle)
                    },
                    isRefreshing = isListRefreshing,
                    onRefresh = {
                        coroutineScope.launch {
                            isListRefreshing = true
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
                            isListRefreshing = false
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
        HomeScreen(
            state = HomeState(
                profileInitials = "NI"
            ),
            onAction = {}
        )
    }
}

@PreviewScreenSizes
@Composable
private fun HomeScreenPreviewScreenSizes() {
    TaskyTheme {
        HomeScreen(
            state = HomeState(
                profileInitials = "NI"
            ),
            onAction = {}
        )
    }
}
