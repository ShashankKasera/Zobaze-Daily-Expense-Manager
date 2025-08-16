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

    private val _expensesAmount = MutableStateFlow<Double>(0.0)
    val expensesAmount: StateFlow<Double> = _expensesAmount

    private val _allExpensesGroupList = MutableStateFlow<Map<String, List<Expenses>>>(emptyMap())
    val allExpensesGroupList: StateFlow<Map<String, List<Expenses>>> = _allExpensesGroupList

    init {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        getTodayTotalExpensesAmount(today)
        fetchTodayExpenses(today)
    }

    fun getTodayTotalExpensesAmount(today: String) {
        viewModelScope.launch {
            expensesRepository.getTodayTotalExpensesAmount(today).collect { amount ->
                _expensesAmount.value = amount
                Log.i("AllExpenses", "Fetched: $amount")
            }
        }
    }

    fun getTotalExpensesAmountInDateRange(startDate: String, endDate: String) {
        viewModelScope.launch {
            expensesRepository.getTotalExpensesAmountInDateRange(startDate,endDate).collect { amount ->
                _expensesAmount.value = amount
                Log.i("AllExpenses", "Fetched: $amount")
            }
        }
    }


    fun fetchExpensesBetweenDates(startDate: String, endDate: String) {
        viewModelScope.launch {
            expensesRepository.loadExpensesBetweenDates(startDate, endDate).collect {
                _allExpensesList.value = it
            }
        }
    }

    fun fetchExpensesByCategory(category: String) {
        viewModelScope.launch {
            expensesRepository.getExpensesByCategory(category).collect {
                _allExpensesList.value = it
            }
        }
    }


    fun fetchTodayExpenses(today: String) {
        viewModelScope.launch {
            expensesRepository.loadExpensesForToday(today).collect {
                _allExpensesList.value = it
            }
        }
    }


    fun groupByCategory() {
        _allExpensesGroupList.value = _allExpensesList.value
            .groupBy { it.category }

    }

    fun getExpensesGroupedByTime() {
        _allExpensesGroupList.value = _allExpensesList.value
            .groupBy { it.date }
    }

}
