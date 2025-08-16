package com.example.zobazedailyexpensemanager.ui.addexpense

import com.example.zobazedailyexpensemanager.core.EMPTY

data class AddExpenseUiState(
    val title: String = String.EMPTY,
    val amount: String = String.EMPTY,
    val category: String = String.EMPTY,
    val notes: String = String.EMPTY,
    val date: String = String.EMPTY,
    val time: String = String.EMPTY,
    val selectedUri: String = String.EMPTY,
    val isCategoryDropdownExpanded: Boolean = false,
    val categories: List<String> = listOf("Staff", "Travel", "Food", "Utility"),
    val titleError: String? = null,
    val amountError: String? = null,
    val categoryError: String? = null,
    val dateError: String? = null,
    val timeError: String? = null,
)