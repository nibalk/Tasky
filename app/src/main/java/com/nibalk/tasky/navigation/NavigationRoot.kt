package com.nibalk.tasky.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nibalk.tasky.auth.presentation.login.LoginScreenRoot
import com.nibalk.tasky.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.AUTH_NAV_GRAPH
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = Route.AUTH_LOGIN_SCREEN,
        route = Route.AUTH_NAV_GRAPH
    ) {
        composable(route = Route.AUTH_LOGIN_SCREEN) {
            LoginScreenRoot(
                onSignUpClick = {
                    navController.navigate(Route.AUTH_REGISTER_SCREEN)
                },
                onSuccessfulLogin = {
                    // TODO: Navigate to Agenda Screen
                }
            )
        }
        composable(route = Route.AUTH_REGISTER_SCREEN) {
            RegisterScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                },
                onSuccessfulRegistration = {
                    //navController.navigateUp()
                }
            )
        }
    }
}
