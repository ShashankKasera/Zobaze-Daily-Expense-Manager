package com.example.zobazedailyexpensemanager.ui.addexpense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.data.repository.ExpensesRepository
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpensesViewModel @Inject constructor(
    private val expensesRepository: ExpensesRepository
) : ViewModel()  {

    val allExpensesList = mutableListOf<Expenses>()

    fun insertExpenses(
        expensesEntity:ExpensesEntity
    ) = viewModelScope.launch(Dispatchers.IO)  {
        expensesRepository.insertExpenses( expensesEntity)}

    fun getAllExpenses() {
        viewModelScope.launch {
            expensesRepository.loadAllExpenses().collect {
                allExpensesList.clear()
                allExpensesList.addAll(it)
                Log.i("rbhj", "getAllExpenses: ${allExpensesList}")
            }
        }
    }
}