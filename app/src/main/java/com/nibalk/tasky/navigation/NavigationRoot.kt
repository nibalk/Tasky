package com.nibalk.tasky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.nibalk.tasky.agenda.presentation.event.EventScreenRoot
import com.nibalk.tasky.agenda.presentation.home.HomeScreenRoot
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.agenda.presentation.reminder.ReminderScreenRoot
import com.nibalk.tasky.agenda.presentation.task.TaskScreenRoot
import com.nibalk.tasky.agenda.presentation.utils.toLocalDate
import com.nibalk.tasky.agenda.presentation.utils.toLongDate
import com.nibalk.tasky.auth.presentation.login.LoginScreenRoot
import com.nibalk.tasky.auth.presentation.register.RegisterScreenRoot
import java.time.LocalDate

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) AgendaNavigationGraph else AuthNavigationGraph
    ) {
        authGraph(navController)
        agendaGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthNavigationGraph>(
        startDestination = AuthLoginScreen,
    ) {
        composable<AuthLoginScreen>  {
            LoginScreenRoot(
                onSignUpClick = {
                    navController.navigate(AuthRegisterScreen) {
                        popUpTo(AuthLoginScreen) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulLogin = {
                    navController.navigate(AgendaNavigationGraph) {
                        popUpTo(AuthNavigationGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AuthRegisterScreen> {
            RegisterScreenRoot(
                onBackClick = {
                    navController.navigate(AuthLoginScreen) {
                        popUpTo(AuthRegisterScreen) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate(AuthLoginScreen)
                }
            )
        }
    }
}

private fun NavGraphBuilder.agendaGraph(navController: NavHostController) {
    navigation<AgendaNavigationGraph>(
        startDestination = AgendaHomeScreen,
    ) {
        composable<AgendaHomeScreen> {
            HomeScreenRoot(
                onDetailClicked = { isEditable, agendaType, agendaItem ->
                    when(agendaType) {
                        AgendaType.EVENT -> navController.navigate(
                            AgendaEventScreen(
                                isEditable = isEditable,
                                agendaId = agendaItem?.id,
                                selectedDate = agendaItem?.startAt?.toLocalDate()?.toLongDate()
                                    ?: LocalDate.now().toLongDate()
                            )
                        )
                        AgendaType.TASK -> navController.navigate(
                            AgendaTaskScreen(
                                isEditable = isEditable,
                                agendaId = agendaItem?.id,
                                selectedDate = agendaItem?.startAt?.toLocalDate()?.toLongDate()
                                    ?: LocalDate.now().toLongDate()
                            )
                        )
                        AgendaType.REMINDER -> navController.navigate(
                            AgendaReminderScreen(
                                isEditable = isEditable,
                                agendaId = agendaItem?.id,
                                selectedDate = agendaItem?.startAt?.toLocalDate()?.toLongDate()
                                    ?: LocalDate.now().toLongDate()
                            )
                        )
                    }
                }
            )
        }
        composable<AgendaEventScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaEventScreen>()
            EventScreenRoot(
                agendaArgs = AgendaArgs(
                    isEditable = args.isEditable,
                    selectedDate = args.selectedDate.toLocalDate(),
                    agendaId = args.agendaId,
                )
            )
        }
        composable<AgendaTaskScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaTaskScreen>()
            TaskScreenRoot(
                agendaArgs = AgendaArgs(
                    isEditable = args.isEditable,
                    selectedDate = args.selectedDate.toLocalDate(),
                    agendaId = args.agendaId,
                )
            )
        }
        composable<AgendaReminderScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaReminderScreen>()
            ReminderScreenRoot(
                agendaArgs = AgendaArgs(
                    isEditable = args.isEditable,
                    selectedDate = args.selectedDate.toLocalDate(),
                    agendaId = args.agendaId,
                )
            )
        }
    }
}
