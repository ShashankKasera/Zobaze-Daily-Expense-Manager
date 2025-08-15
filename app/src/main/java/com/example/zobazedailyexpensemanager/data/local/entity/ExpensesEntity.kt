package com.example.zobazedailyexpensemanager.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpensesEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    val title: String?,
    val amount: Double?,
    val category: String?,
    val notes: String?,
    val receipt: String?,
    var date: String?,
    var time: String?,
)
