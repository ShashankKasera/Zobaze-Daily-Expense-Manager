package com.example.zobazedailyexpensemanager.ui.mapper

import com.example.zobazedailyexpensemanager.core.EMPTY
import com.example.zobazedailyexpensemanager.core.ListMapper
import com.example.zobazedailyexpensemanager.core.Mapper
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.DailyExpenseReport
import javax.inject.Inject

class DailyExpenseReportMapper @Inject constructor() :
    Mapper<ExpensesEntity?, DailyExpenseReport> {
    override fun map(input: ExpensesEntity?) = DailyExpenseReport(
        totalAmount = input?.amount ?: 0.0,
        date = input?.date ?: String.EMPTY,
    )
}

class DailyExpenseReportListMapper @Inject constructor(
    private val dailyExpenseReportMapper: DailyExpenseReportMapper
) : ListMapper<ExpensesEntity, DailyExpenseReport> {

    override fun map(input: List<ExpensesEntity>): List<DailyExpenseReport> {
        return input.map { dailyExpenseReportMapper.map(it) }
    }
}