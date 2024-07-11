package com.nibalk.tasky.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.nibalk.tasky.agenda.presentation.home.HomeScreenRoot
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.auth.presentation.login.LoginScreenRoot
import com.nibalk.tasky.auth.presentation.register.RegisterScreenRoot

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
                                agendaId = agendaItem?.id
                            )
                        )
                        AgendaType.TASK -> navController.navigate(
                            AgendaTaskScreen(
                                isEditable = isEditable,
                                agendaId = agendaItem?.id
                            )
                        )
                        AgendaType.REMINDER -> navController.navigate(
                            AgendaReminderScreen(
                                isEditable = isEditable,
                                agendaId = agendaItem?.id
                            )
                        )
                    }
                }
            )
        }
        composable<AgendaEventScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaEventScreen>()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Event Screen with ID: ${args.agendaId} and Screen Editable: ${args.isEditable}")
            }
        }
        composable<AgendaTaskScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaTaskScreen>()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Task Screen with ID: ${args.agendaId} and Screen Editable: ${args.isEditable}")
            }
        }
        composable<AgendaReminderScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaReminderScreen>()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Reminder Screen with ID: ${args.agendaId} and Screen Editable: ${args.isEditable}")
            }
        }
    }
}
