package com.example.zobazedailyexpensemanager.data.repository

import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {

    suspend fun insertExpenses(expenses: ExpensesEntity)

    suspend fun loadAllExpenses(): Flow<List<Expenses>>
}