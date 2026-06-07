package com.miladofficial.bankvam.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miladofficial.bankvam.presentation.screens.AddEditLoanScreen
import com.miladofficial.bankvam.presentation.screens.DashboardScreen
import com.miladofficial.bankvam.presentation.screens.LoanListScreen

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object LoanList : Screen("loan_list")
    object AddEditLoan : Screen("add_edit_loan/{loanId}") {
        fun passId(loanId: Long = 0) = "add_edit_loan/$loanId"
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToLoanList = {
                    navController.navigate(Screen.LoanList.route)
                }
            )
        }

        composable(Screen.LoanList.route) {
            LoanListScreen(
                onNavigateToAddEdit = { loanId ->
                    navController.navigate(Screen.AddEditLoan.passId(loanId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.AddEditLoan.route,
            arguments = listOf(navArgument("loanId") { type = NavType.LongType })
        ) { backStackEntry ->
            val loanId = backStackEntry.arguments?.getLong("loanId") ?: 0L
            AddEditLoanScreen(
                loanId = loanId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
