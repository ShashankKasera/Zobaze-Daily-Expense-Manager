package com.example.zobazedailyexpensemanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity

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
}