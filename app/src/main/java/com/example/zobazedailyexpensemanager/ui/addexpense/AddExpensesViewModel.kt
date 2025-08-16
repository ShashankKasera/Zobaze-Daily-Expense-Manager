package com.example.zobazedailyexpensemanager.ui.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobazedailyexpensemanager.R
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState

    fun onTitleChanged(newValue: String) {
        _uiState.update { it.copy(title = newValue) }
    }

    fun onAmountChanged(newValue: String) {
        if (newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
            _uiState.update { it.copy(amount = newValue) }
        }
    }

    fun onCategorySelected(newValue: String) {
        _uiState.update { it.copy(category = newValue, isCategoryDropdownExpanded = false) }
    }

    fun onNotesChanged(newValue: String) {
        if (newValue.length <= 100) {
            _uiState.update { it.copy(notes = newValue) }
        }
    }

    fun onDateSelected(newValue: String) {
        _uiState.update { it.copy(date = newValue) }
    }

    fun onTimeSelected(newValue: String) {
        _uiState.update { it.copy(time = newValue) }
    }

    fun onImageSelected(newUri: String) {
        _uiState.update { it.copy(selectedUri = newUri) }
    }

    fun toggleCategoryDropdown() {
        _uiState.update { it.copy(isCategoryDropdownExpanded = !it.isCategoryDropdownExpanded) }
    }

    fun insertExpenses(expense: ExpensesEntity) {
        viewModelScope.launch {
            expensesRepository.insertExpenses(expense)
        }
    }

    fun resetForm() {
        _uiState.value = AddExpenseUiState()
    }

    fun validateInputs(): Boolean {
        val titleError = if (uiState.value.title.isBlank()) "Title cannot be empty" else null

        val amountError = when {
            uiState.value.amount.isBlank() -> "Amount cannot be empty"
            uiState.value.amount.toDoubleOrNull() == null || uiState.value.amount.toDouble() <= 0 ->
                "Enter a valid amount greater than 0"
            else -> null
        }

        val categoryError = if (uiState.value.category.isBlank()) "Please select a category" else null

        val dateError = if (uiState.value.date.isBlank()) "Please select a date" else null

        val timeError = if (uiState.value.time.isBlank()) "Please select a time" else null

        val hasError = titleError != null || amountError != null || categoryError != null

        _uiState.update {
            it.copy(
                titleError = titleError,
                amountError = amountError,
                categoryError = categoryError,
                dateError = dateError,
                timeError = timeError
            )
        }

        return !hasError
    }



}