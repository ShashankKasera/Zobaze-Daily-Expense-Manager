package com.example.zobazedailyexpensemanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDao {

    @Insert
    fun insertExpenses(expenses: ExpensesEntity)

    @Update
    suspend fun upDateExpenses(expenses: ExpensesEntity)

    @Delete
    suspend fun deleteExpenses(expenses: ExpensesEntity)

    @Insert
    fun insertAllExpenses(vararg expenses: ExpensesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExpenses(users: List<ExpensesEntity>)

    @Query("Select * from ExpensesEntity")
    fun loadAllExpenses(): List<ExpensesEntity>

    @Query("SELECT * FROM ExpensesEntity WHERE date = :todayDate")
    fun getExpensesForToday(todayDate: String): List<ExpensesEntity>

    @Query("SELECT * FROM ExpensesEntity WHERE date BETWEEN :startDate AND :endDate")
    fun getExpensesBetweenDates(startDate: String, endDate: String): List<ExpensesEntity>

    @Query("SELECT * FROM ExpensesEntity WHERE category = :category")
    fun getExpensesByCategory(category: String): List<ExpensesEntity>

    @Query("SELECT SUM(amount) FROM ExpensesEntity")
    fun getTotalExpensesAmount(): Flow<Double>

    @Query("""
    SELECT * FROM ExpensesEntity 
    WHERE date >= :startDate
    ORDER BY date DESC""")
    fun getExpensesLast7Days(startDate: String): List<ExpensesEntity>
}