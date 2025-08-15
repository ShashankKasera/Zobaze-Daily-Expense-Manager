package com.example.zobazedailyexpensemanager.ui.addexpense.repository

import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import kotlinx.coroutines.flow.Flow

interface AddExpensesRepository {

    suspend fun insertExpenses(expenses: ExpensesEntity)

    suspend fun loadAllExpenses(): Flow<List<Expenses>>
}