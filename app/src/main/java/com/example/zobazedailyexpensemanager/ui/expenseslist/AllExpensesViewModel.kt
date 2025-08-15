package com.example.zobazedailyexpensemanager.ui.expenseslist

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepository
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AllExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    private val masterList = mutableListOf<Expenses>()

    private val _allExpensesList = MutableStateFlow<List<Expenses>>(emptyList())
    val allExpensesList: StateFlow<List<Expenses>> = _allExpensesList

    private val _todayExpenses = MutableStateFlow<List<Expenses>>(emptyList())
    val todayExpenses: StateFlow<List<Expenses>> = _todayExpenses


    private val _expensesInRange = MutableStateFlow<List<Expenses>>(emptyList())
    val expensesInRange: StateFlow<List<Expenses>> = _expensesInRange

    private val _expensesByCategory = MutableStateFlow<List<Expenses>>(emptyList())
    val expensesByCategory: StateFlow<List<Expenses>> = _expensesByCategory

    private val _TotalExpensesAmount = MutableStateFlow<Double>(0.0)
    val totalExpensesAmount: StateFlow<Double> = _TotalExpensesAmount



    init {
        getAllExpenses()
        fetchTodayExpenses()
        getTotalExpensesAmount()

    }


    fun fetchExpensesBetweenDates(startDate: String, endDate: String) {
        viewModelScope.launch {
            expensesRepository.loadExpensesBetweenDates(startDate, endDate).collect {
                _expensesInRange.value = it
            }
        }
    }



    fun fetchExpensesByCategory(category: String) {
        viewModelScope.launch {
            expensesRepository.getExpensesByCategory(category).collect {
                _expensesByCategory.value = it
            }
        }
    }
    fun fetchTodayExpenses() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            expensesRepository.loadExpensesForToday(today).collect {
                _todayExpenses.value = it
            }
        }
    }
    fun getAllExpenses() {
        viewModelScope.launch {
            expensesRepository.loadAllExpenses().collect { list ->
                masterList.clear()
                masterList.addAll(list)
                _allExpensesList.value = list
                Log.i("AllExpenses", "Fetched: $list")
            }
        }
    }

    private fun getTotalExpensesAmount() {
        viewModelScope.launch {
            expensesRepository.getTotalExpensesAmount().collect { amount ->
                _TotalExpensesAmount.value = amount
                Log.i("AllExpenses", "Fetched: $amount")
            }
        }
    }




}
