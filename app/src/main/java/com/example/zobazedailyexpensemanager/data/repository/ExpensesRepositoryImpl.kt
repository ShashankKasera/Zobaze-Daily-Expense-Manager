package com.example.zobazedailyexpensemanager.data.repository

import com.example.zobazedailyexpensemanager.data.local.ExpensesDao
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.mapper.ExpensesListMapper
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExpensesRepositoryImpl @Inject constructor(
    private val expensesDao: ExpensesDao,
    private val expensesListMapper: ExpensesListMapper,
) : ExpensesRepository {

    override suspend fun insertExpenses(expenses: ExpensesEntity) = withContext(Dispatchers.IO) {
        expensesDao.insertExpenses(expenses)
    }

    override suspend fun loadAllExpenses(): Flow<List<Expenses>> = flow {
        emit(expensesListMapper.map(expensesDao.loadAllExpenses()))
    }.flowOn(Dispatchers.IO)

    override suspend fun loadExpensesForToday(today: String): Flow<List<Expenses>> = flow {
        emit(expensesListMapper.map(expensesDao.getExpensesForToday(today)))
    }.flowOn(Dispatchers.IO)

    override suspend fun loadExpensesBetweenDates(
        startDate: String,
        endDate: String
    ): Flow<List<Expenses>> = flow {
        emit(expensesListMapper.map(expensesDao.getExpensesBetweenDates(startDate, endDate)))
    }.flowOn(Dispatchers.IO)

    override suspend fun getExpensesByCategory(category: String): Flow<List<Expenses>> = flow {
        emit(expensesListMapper.map(expensesDao.getExpensesByCategory(category)))
    }.flowOn(Dispatchers.IO)

    override suspend fun getTotalExpensesAmount(): Flow<Double> =
        (expensesDao.getTotalExpensesAmount()).flowOn(Dispatchers.IO)


    override fun getExpensesLast7Days(startDate: String): Flow<List<Expenses>> = flow {
        emit(expensesListMapper.map(expensesDao.getExpensesLast7Days(startDate)))
    }.flowOn(Dispatchers.IO)


}