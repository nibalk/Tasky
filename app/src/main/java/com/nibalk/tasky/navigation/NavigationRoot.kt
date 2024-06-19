package com.nibalk.tasky.navigation

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
        startDestination = AuthNavigationGraph
    ) {
        authGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<AuthNavigationGraph>(
        startDestination = AuthLoginScreen,
    ) {
        composable<AuthLoginScreen>  {
            LoginScreenRoot(
                onSignUpClick = {
                    navController.navigate(AuthRegisterScreen)
                },
                onSuccessfulLogin = {
                    // TODO: Navigate to Agenda Screen
                }
            )
        }
        composable<AuthRegisterScreen> {
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

