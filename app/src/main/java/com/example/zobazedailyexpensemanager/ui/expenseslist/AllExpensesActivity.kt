package com.example.zobazedailyexpensemanager.ui.expenseslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.width
import androidx.compose.ui.platform.LocalContext
import java.time.format.DateTimeFormatter
import java.util.Calendar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

@AndroidEntryPoint
class AllExpensesActivity : ComponentActivity() {

    private val viewModel: AllExpensesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
//                viewModel.fetchExpensesBetweenDates("2025-08-14", "2025-08-17")
                viewModel.fetchExpensesByCategory("Food")
                ExpensesScreen(viewModel)


            }
        }
    }
}

@Composable
fun ExpensesScreen(viewModel: AllExpensesViewModel) {
//    val expenses by viewModel.allExpensesList.collectAsState()
//    val expenses by viewModel.todayExpenses.collectAsState()
//    val expenses by viewModel.todayExpenses.collectAsState()
    val totalAmount by viewModel.totalExpensesAmount.collectAsState()



    val expenses by viewModel.expensesByCategory.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Total Amount at the top
        Text(
            text = "Total Expense : Rs. %.2f".format(totalAmount),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "All Expenses",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (expenses.isEmpty()) {
            Text("No Expenses till now")
        } else {
            LazyColumn {
                items(expenses) { expense ->
                    ExpenseItem(expense)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun ExpenseItem(expense: Expenses) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = expense.title ?: "Untitled",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "₹${expense.amount ?: 0.0}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Category: ${expense.category ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall
            )

            if (!expense.notes.isNullOrEmpty()) {
                Text(
                    text = "Notes: ${expense.notes}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Date: ${expense.date ?: "-"} ${expense.time ?: ""}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

