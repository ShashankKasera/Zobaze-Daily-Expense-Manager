package com.example.zobazedailyexpensemanager.ui.expenseslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DensitySmall
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.zobazedailyexpensemanager.R

@Composable
fun BottomActionButton(
    onAddExpenseClick: () -> Unit,
    onExpenseReportClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(horizontalAlignment = Alignment.End) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.add_expense)) },
                    onClick = {
                        expanded = false
                        onAddExpenseClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.expense_report)) },
                    onClick = {
                        expanded = false
                        onExpenseReportClick()
                    }
                )
            }

            FloatingActionButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.DensitySmall, contentDescription = stringResource(R.string.options))
            }
        }
    }
}
