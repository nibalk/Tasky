package com.nibalk.tasky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nibalk.tasky.agenda.presentation.home.HomeScreen
import com.nibalk.tasky.agenda.presentation.home.HomeScreenRoot
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
                onDetailClicked = {}
            )
        }
    }
}
