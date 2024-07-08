package com.nibalk.tasky.agenda.presentation.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.agenda.presentation.components.AgendaAddButton
import com.nibalk.tasky.agenda.presentation.components.AgendaCard
import com.nibalk.tasky.agenda.presentation.components.AgendaDayPicker
import com.nibalk.tasky.agenda.presentation.components.AgendaHeader
import com.nibalk.tasky.agenda.presentation.components.AgendaRefreshableList
import com.nibalk.tasky.agenda.presentation.model.AgendaItemActionType
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.agenda.presentation.utils.getSurroundingDays
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoot(
    onDetailClicked: (Boolean) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.uiEvent) { event ->
        when(event) {
            is HomeEvent.FetchAgendaError -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is HomeEvent.FetchAgendaSuccess -> {
                Toast.makeText(
                    context,
                    R.string.agenda_items_list_loaded,
                    Toast.LENGTH_LONG
                ).show()
            }
            is HomeEvent.LogoutError -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is HomeEvent.LogoutSuccess -> {
                Toast.makeText(
                    context,
                    R.string.agenda_logout_successful,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    HomeScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is HomeAction.OnListItemOptionOpenClick -> onDetailClicked(false)
                is HomeAction.OnListItemOptionEditClick -> onDetailClicked(true)
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
    val context = LocalContext.current

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
                    onLogoutClicked = {
                        Toast.makeText(context,"Logout clicked!", Toast.LENGTH_LONG).show()
                        onAction(HomeAction.OnLogoutClicked)
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
                    modifier = Modifier.fillMaxWidth(),
                    items = state.agendaItems,
                    content = { item ->
                        val agendaType: AgendaType = when (item) {
                            is AgendaItem.Task -> AgendaType.TASK
                            is AgendaItem.Event -> AgendaType.EVENT
                            is AgendaItem.Reminder -> AgendaType.REMINDER
                        }
                        AgendaCard(
                            item = item,
                            agendaType = agendaType,
                            onItemClick = {},
                            onIsDone = {}
                        ) { actionType ->
                            when(actionType) {
                                AgendaItemActionType.OPEN -> {
                                    onAction(HomeAction.OnListItemOptionOpenClick(item, agendaType))
                                }
                                AgendaItemActionType.EDIT -> {
                                    onAction(HomeAction.OnListItemOptionEditClick(item, agendaType))
                                }
                                AgendaItemActionType.DELETE -> {
                                    onAction(HomeAction.OnListItemOptionDeleteClick(item, agendaType))
                                }
                            }
                        }
                    },
                    isRefreshing = state.isLoading,
                    onRefresh = {
                        onAction(HomeAction.OnAgendaListRefreshed)
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
                profileInitials = "AB"
            ),
            onAction = {}
        )
    }
}
