package com.example.zobazedailyexpensemanager.ui.expensereport

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepository
import com.example.zobazedailyexpensemanager.ui.model.CategoryExpenseReport
import com.example.zobazedailyexpensemanager.ui.model.DailyExpenseReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseReportViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel() {


    private val _DailyExpenseReport = MutableStateFlow<List<DailyExpenseReport>>(emptyList())
    val dailyExpenseReport: StateFlow<List<DailyExpenseReport>> = _DailyExpenseReport

    private val _CategoryExpenseReport = MutableStateFlow<List<CategoryExpenseReport>>(emptyList())
    val categoryExpenseReport: StateFlow<List<CategoryExpenseReport>> = _CategoryExpenseReport

    init {
        getLast7DaysExpensesReportDateWise()
        getLast7DaysExpensesReportCategoryWise()
    }


    private fun getLast7DaysExpensesReportDateWise() {
        viewModelScope.launch {
            expensesRepository.getLast7DaysExpensesReportDateWise().collect { it ->
                _DailyExpenseReport.value = it
            }
        }
    }

    private fun getLast7DaysExpensesReportCategoryWise() {
        viewModelScope.launch {
            expensesRepository.getLast7DaysExpensesReportCategoryWise().collect { it ->
                _CategoryExpenseReport.value = it
            }
        }
    }

}

