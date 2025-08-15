package com.example.zobazedailyexpensemanager.ui.expenseslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepository
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val _allExpensesList = MutableStateFlow<List<Expenses>>(emptyList())
    val allExpensesList: StateFlow<List<Expenses>> = _allExpensesList

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            expensesRepository.loadAllExpenses().collect { list ->
                _allExpensesList.value = list
                Log.i("AllExpenses", "Fetched: $list")
            }
        }
    }
}
