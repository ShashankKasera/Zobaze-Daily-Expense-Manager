package com.example.zobazedailyexpensemanager.ui

import com.example.zobazedailyexpensemanager.core.EMPTY
import com.example.zobazedailyexpensemanager.core.ListMapper
import com.example.zobazedailyexpensemanager.core.Mapper
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.Expenses
import javax.inject.Inject

class ExpensesMapper @Inject constructor() :
    Mapper<ExpensesEntity?, Expenses> {
    override fun map(input: ExpensesEntity?) = Expenses(
        id = input?.id ?: -1,
        title = input?.title?:String.EMPTY,
        amount = input?.amount ?: 0.0,
        category = input?.category ?: String.EMPTY,
        notes = input?.notes ?: String.EMPTY,
        receipt = input?.receipt ?: String.EMPTY,
        date = input?.date ?: String.EMPTY,
        time = input?.time ?: String.EMPTY,
    )
}

class ExpensesListMapper @Inject constructor(
    private val expensesMapper: ExpensesMapper
) : ListMapper<ExpensesEntity, Expenses> {

    override fun map(input: List<ExpensesEntity>): List<Expenses> {
        return input.map { expensesMapper.map(it) }
    }
}