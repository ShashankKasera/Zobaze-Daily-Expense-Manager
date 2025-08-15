package com.example.zobazedailyexpensemanager.data.repository

import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    suspend fun insertExpenses(expenses: ExpensesEntity)

    suspend fun loadAllExpenses(): Flow<List<Expenses>>
    suspend fun loadExpensesForToday(today: String): Flow<List<Expenses>>
    suspend fun loadExpensesBetweenDates(startDate: String, endDate: String): Flow<List<Expenses>>
    suspend fun getExpensesByCategory(category: String): Flow<List<Expenses>>
    suspend fun getTotalExpensesAmount(): Flow<Double>
    fun getExpensesLast7Days(startDate: String): Flow<List<Expenses>>
}