package com.example.zobazedailyexpensemanager.ui.navigation

sealed class Route(val route: String) {
    object AddExpenses : Route("add_expenses")
    object ExpenseReport : Route("expense_report")
    object AllExpenses : Route("all_expenses")
}