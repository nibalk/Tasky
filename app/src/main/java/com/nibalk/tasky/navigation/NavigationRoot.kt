package com.nibalk.tasky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.nibalk.tasky.agenda.presentation.detail.DetailScreenRoot
import com.nibalk.tasky.agenda.presentation.editor.EditorScreenRoot
import com.nibalk.tasky.agenda.presentation.home.HomeScreenRoot
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import com.nibalk.tasky.auth.presentation.login.LoginScreenRoot
import com.nibalk.tasky.auth.presentation.register.RegisterScreenRoot
import com.nibalk.tasky.core.presentation.utils.toLocalDate
import com.nibalk.tasky.core.presentation.utils.toLongDate
import timber.log.Timber
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
                    navController.navigate(
                        AgendaDetailScreen(
                            isEditable = isEditable,
                            selectedDate = agendaItem?.startAt?.toLocalDate()?.toLongDate()
                                ?: LocalDate.now().toLongDate(),
                            agendaId = agendaItem?.id,
                            agendaType = agendaType.name
                        )
                    )
                }
            )
        }
        composable<AgendaDetailScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaDetailScreen>()
            DetailScreenRoot(
                navController = navController,
                onCloseClicked = {
                    navController.popBackStack(
                        route = AgendaHomeScreen,
                        inclusive = false,
                    )
                },
                onEditorClicked = { text, type ->
                    Timber.d("[NavIssueLogs] text = %s", text)
                    Timber.d("[NavIssueLogs] type = %s", type)
                    navController.navigate(
                        AgendaEditorScreen(
                            editorText = text,
                            editorType = type.name,
                            agendaType = args.agendaType
                        )
                    )
                },
                agendaArgs = AgendaArgs(
                    isEditable = args.isEditable,
                    selectedDate = args.selectedDate.toLocalDate(),
                    agendaId = args.agendaId,
                    agendaType = args.agendaType
                )
            )
        }
        composable<AgendaEditorScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<AgendaEditorScreen>()
            EditorScreenRoot(
                onBackClicked = {
                    navController.navigateUp()
                },
                onSaveClicked = { text, type ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        type.name, text
                    )
                    navController.navigateUp()
                },
                editorArgs = EditorArgs(
                    editorText = args.editorText,
                    editorType = args.editorType,
                )
            )
        }
    }
}
