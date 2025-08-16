package com.example.zobazedailyexpensemanager.ui.addexpense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zobazedailyexpensemanager.R
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navigate: (String) -> Unit, onBackClick: (() -> Unit)) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_expenses)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { innerPadding ->
            AddExpenseMainContent(innerPadding)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseMainContent(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val viewModel: AddExpensesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.onImageSelected(it.toString())
        }
    }

    val calendar = remember { Calendar.getInstance() }
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                viewModel.onDateSelected(
                    String.format(
                        context.getString(R.string._04d_02d_02d),
                        year,
                        month + 1,
                        dayOfMonth
                    )
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                viewModel.onTimeSelected(
                    String.format(
                        context.getString(R.string._02d_02d),
                        hour,
                        minute
                    )
                )
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = uiState.title,
            onValueChange = { viewModel.onTitleChanged(it) },
            label = { Text(stringResource(R.string.title)) },
            isError = uiState.titleError != null,
            supportingText = {
                uiState.titleError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )




        OutlinedTextField(
            value = uiState.amount,
            onValueChange = { viewModel.onAmountChanged(it) },
            label = { Text(stringResource(R.string.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = uiState.amountError != null,
            supportingText = {
                uiState.amountError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = uiState.isCategoryDropdownExpanded,
            onExpandedChange = { viewModel.toggleCategoryDropdown() }
        ) {
            OutlinedTextField(
                value = uiState.category,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.category)) },
                isError = uiState.categoryError != null,
                supportingText = {
                    uiState.categoryError?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isCategoryDropdownExpanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )


            ExposedDropdownMenu(
                expanded = uiState.isCategoryDropdownExpanded,
                onDismissRequest = { viewModel.toggleCategoryDropdown() }
            ) {
                uiState.categories.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = { viewModel.onCategorySelected(selectionOption) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.notes,
            onValueChange = { viewModel.onNotesChanged(it) },
            label = { Text(stringResource(R.string.optional_notes_max_100_chars)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3,
            supportingText = { Text(stringResource(R.string._100, uiState.notes.length)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.date,
            onValueChange = {},
            label = { Text(stringResource(R.string.select_date)) },
            isError = uiState.dateError != null,
            supportingText = {
                uiState.dateError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            enabled = false,
            readOnly = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.time,
            onValueChange = {},
            label = { Text(stringResource(R.string.select_time)) },
            isError = uiState.timeError != null,
            supportingText = {
                uiState.timeError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() },
            enabled = false,
            readOnly = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = { launcher.launch(context.getString(R.string.image)) }) {
                Text(stringResource(R.string.pick_image))
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = uiState.selectedUri,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (viewModel.validateInputs()) {
                    val expense = ExpensesEntity(
                        id = null,
                        title = uiState.title.ifEmpty { null },
                        amount = uiState.amount.toDoubleOrNull(),
                        category = uiState.category.ifEmpty { null },
                        notes = uiState.notes.ifEmpty { null },
                        receipt = uiState.selectedUri.ifEmpty { null },
                        date = uiState.date.ifEmpty { null },
                        time = uiState.time.ifEmpty { null },
                    )

                    viewModel.insertExpenses(expense)
                    Toast.makeText(
                        context,
                        context.getString(R.string.expense_added_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetForm()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_expense))
        }
    }
}
