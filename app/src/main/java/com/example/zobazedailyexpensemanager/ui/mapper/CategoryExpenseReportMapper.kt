package com.example.zobazedailyexpensemanager.ui.mapper

import com.example.zobazedailyexpensemanager.core.EMPTY
import com.example.zobazedailyexpensemanager.core.ListMapper
import com.example.zobazedailyexpensemanager.core.Mapper
import com.example.zobazedailyexpensemanager.data.local.entity.ExpensesEntity
import com.example.zobazedailyexpensemanager.ui.model.CategoryExpenseReport
import javax.inject.Inject

class CategoryExpenseReportMapper @Inject constructor() :
    Mapper<ExpensesEntity?, CategoryExpenseReport> {
    override fun map(input: ExpensesEntity?) = CategoryExpenseReport(
        totalAmount = input?.amount ?: 0.0,
        category = input?.category ?: String.EMPTY,
    )
}

class CategoryExpenseReportListMapper @Inject constructor(
    private val categoryExpenseReportMapper: CategoryExpenseReportMapper
) : ListMapper<ExpensesEntity, CategoryExpenseReport> {

    override fun map(input: List<ExpensesEntity>): List<CategoryExpenseReport> {
        return input.map { categoryExpenseReportMapper.map(it) }
    }
}