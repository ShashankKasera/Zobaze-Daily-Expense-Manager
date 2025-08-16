package com.example.zobazedailyexpensemanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zobazedailyexpensemanager.ui.addexpense.AddExpenseScreen
import com.example.zobazedailyexpensemanager.ui.expensereport.ExpenseReportScreen
import com.example.zobazedailyexpensemanager.ui.expenseslist.AllExpensesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.AllExpenses.route) {
        composable(Route.AddExpenses.route) {
            AddExpenseScreen(
                navigate = {
                    navController.navigate(it)
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
        composable(Route.ExpenseReport.route) {
            ExpenseReportScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
        composable(Route.AllExpenses.route) {
            AllExpensesScreen(
                navigate = {
                    navController.navigate(it)
                }
            )
        }
    }
}