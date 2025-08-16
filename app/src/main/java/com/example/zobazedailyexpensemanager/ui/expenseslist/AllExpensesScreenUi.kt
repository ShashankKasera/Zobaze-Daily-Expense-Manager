package com.example.zobazedailyexpensemanager.ui.expenseslist

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import com.example.zobazedailyexpensemanager.ui.navigation.Route
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllExpensesScreen(
    navigate: (String) -> Unit,
) {

    val viewModel: AllExpensesViewModel = hiltViewModel()
    val totalAmount by viewModel.expensesAmount.collectAsState()
    val expenses by viewModel.allExpensesList.collectAsState()
    val allExpensesGroupList by viewModel.allExpensesGroupList.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var group by remember { mutableStateOf(false) }
    var activeSheet by remember { mutableStateOf(BottomSheetType.SORT) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isStartPickerVisible by remember { mutableStateOf(false) }
    var isEndPickerVisible by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("") }


    DatePiker(isStartPickerVisible, {
        startDate = it
        isStartPickerVisible = false
        isEndPickerVisible = true
    })

    DatePiker(isEndPickerVisible, {
        viewModel.fetchExpensesBetweenDates(startDate, it)
        viewModel.getTotalExpensesAmountInDateRange(startDate, it)
        isEndPickerVisible = false
    })

    ExpenseScreenWithFilterAndSort(
        activeSheet = activeSheet,
        showBottomSheet = showBottomSheet,
        sheetState = sheetState,
        onDismiss = {
            coroutineScope.launch {
                showBottomSheet = false
                sheetState.hide()
            }
        },
        onFilterClick = {
            showBottomSheet = false
            when (it) {
                "Today" -> {
                    group = false
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    viewModel.fetchTodayExpenses(today)
                    viewModel.getTodayTotalExpensesAmount(today)
                }

                "Previous dates via calendar" -> {
                    group = false
                    isStartPickerVisible = true
                }


                else -> {
                    group = false
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    viewModel.fetchTodayExpenses(today)
                    viewModel.getTodayTotalExpensesAmount(today)
                }
            }
        },
        onSorClick = {
            showBottomSheet = false
            when (it) {
                "Group by Category" -> {
                    group = true
                    viewModel.groupByCategory()
                }

                else -> {
                    group = true
                    viewModel.getExpensesGroupedByTime()
                }
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Expenses") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            activeSheet = BottomSheetType.FILTER
                            showBottomSheet = true
                            sheetState.show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        coroutineScope.launch {
                            activeSheet = BottomSheetType.SORT
                            showBottomSheet = true
                            sheetState.show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Sort,
                            contentDescription = "Sort",
                            tint = Color.White
                        )
                    }
                }

            )
        },
        content = { innerPadding ->
            AllExpensesMainContent(
                totalAmount = totalAmount,
                expenses = expenses,
                navigate = navigate,
                innerPadding = innerPadding,
                group = group,
                allExpensesGroupList = allExpensesGroupList
            )
        }
    )
}

@Composable
fun DateRangePickerButton() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // State for selected dates
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    // Flags to track which picker is open
    var isStartPickerVisible by remember { mutableStateOf(false) }
    var isEndPickerVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {

        // Start Date Picker
        Button(onClick = { isStartPickerVisible = true }) {
            Text(text = if (startDate.isEmpty()) "Select Start Date" else "Start: $startDate")
        }

        // End Date Picker
        Button(onClick = { isEndPickerVisible = true }) {
            Text(text = if (endDate.isEmpty()) "Select End Date" else "End: $endDate")
        }

        // Show Date Picker Dialog for Start Date
        if (isStartPickerVisible) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    startDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                    isStartPickerVisible = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Show Date Picker Dialog for End Date
        if (isEndPickerVisible) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    endDate = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
                    isEndPickerVisible = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Use the selected range
        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            Text("Selected range:\n$startDate to $endDate")
        }
    }
}


@Composable
fun GroupedExpensesScreen(map: Map<String, List<Expenses>>) {

    Column {
        LazyColumn {
            map.forEach { (category, expenses) ->
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(expenses) { expense ->
                    ExpenseItem(expense)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}


@Composable
fun AllExpensesMainContent(
    navigate: (String) -> Unit,
    innerPadding: PaddingValues,
    expenses: List<Expenses>,
    totalAmount: Double,
    group: Boolean,
    allExpensesGroupList: Map<String, List<Expenses>>
) {

    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
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

            if (group) {
                if (allExpensesGroupList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                        Text("No Expenses found")
                    }
                }
                else{
                    GroupedExpensesScreen(map = allExpensesGroupList)
                }
            } else {
                if (expenses.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f)){
                        Text("No Expenses found")
                    }
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

        BottomActionButton(
            onAddExpenseClick = {
                navigate(Route.AddExpenses.route)
            },
            onExpenseReportClick = {
                navigate(Route.ExpenseReport.route)
            }
        )
    }
}


@Composable
fun ExpenseItem(expense: Expenses) {
    val context = LocalContext.current
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

            Row {
                Column (modifier = Modifier.weight(1f)){
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
                AsyncImage(
                    model = expense.receipt.toUri(),
                    contentDescription = "Saved Image",
                    modifier = Modifier.size(60.dp).padding(8.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreenWithFilterAndSort(
    activeSheet: BottomSheetType,
    onDismiss: () -> Unit,
    showBottomSheet: Boolean,
    sheetState: SheetState,
    onFilterClick: (String) -> Unit,
    onSorClick: (String) -> Unit
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState
        ) {
            when (activeSheet) {
                BottomSheetType.FILTER -> FilterSheetContent { selected ->
                    // Handle filter option
                    println("Filter selected: $selected")
                    onFilterClick(selected)
                }

                BottomSheetType.SORT -> SortSheetContent { selected ->
                    // Handle sort option
                    println("Sort selected: $selected")
                    onSorClick(selected)
                }
            }
        }
    }
}

enum class BottomSheetType {
    FILTER, SORT
}

@Composable
fun FilterSheetContent(onOptionSelected: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Filter Expenses By", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        FilterOption("Today", onOptionSelected)
        FilterOption("Previous dates via calendar", onOptionSelected)
    }
}

@Composable
fun SortSheetContent(onOptionSelected: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sort Expenses By", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        FilterOption("Group by Category", onOptionSelected)
        FilterOption("Group by Time", onOptionSelected)
    }
}

@Composable
fun FilterOption(label: String, onClick: (String) -> Unit) {
    TextButton(
        onClick = { onClick(label) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label)
    }
}

@Composable
fun DatePiker(
    isShow: Boolean,
    onDatePicked: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (isShow) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDatePicked("%04d-%02d-%02d".format(year, month + 1, dayOfMonth))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}






