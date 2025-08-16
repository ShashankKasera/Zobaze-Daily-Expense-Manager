package com.example.zobazedailyexpensemanager.data.repository

import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.CategoryExpenseReport
import com.example.zobazedailyexpensemanager.ui.model.DailyExpenseReport
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    suspend fun insertExpenses(expenses: ExpensesEntity)

    suspend fun loadAllExpenses(): Flow<List<Expenses>>
    suspend fun loadExpensesForToday(today: String): Flow<List<Expenses>>
    suspend fun loadExpensesBetweenDates(startDate: String, endDate: String): Flow<List<Expenses>>
    suspend fun getExpensesByCategory(category: String): Flow<List<Expenses>>
    suspend fun getTotalExpensesAmount(): Flow<Double>
    suspend fun getTodayTotalExpensesAmount(today: String): Flow<Double>
    suspend fun getTotalExpensesAmountInDateRange(startDate: String,endDate:String): Flow<Double>
    suspend fun getLast7DaysExpensesReportDateWise(): Flow<List<DailyExpenseReport>>
    suspend fun getLast7DaysExpensesReportCategoryWise(): Flow<List<CategoryExpenseReport>>
    suspend fun getOneExpensePerCategory(): Flow<List<Expenses>>
}