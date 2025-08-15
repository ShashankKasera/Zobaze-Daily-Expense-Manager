package com.example.zobazedailyexpensemanager.ui.addexpense.repository

import com.example.zobazedailyexpensemanager.data.local.ExpensesDao
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.ExpensesListMapper
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddExpensesRepositoryImpl @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val expensesListMapper: ExpensesListMapper,
) : AddExpensesRepository{

    override suspend fun insertExpenses(expenses: ExpensesEntity) = withContext(Dispatchers.IO) {
        expensesDao.insertExpenses(expenses)
    }

    override suspend fun loadAllExpenses(): Flow<List<Expenses>> = flow {
        emit(expensesListMapper.map(expensesDao.loadAllExpenses()))
    }.flowOn(Dispatchers.IO)



}