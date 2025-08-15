package com.example.zobazedailyexpensemanager.ui.model

import com.example.zobazedailyexpensemanager.core.EMPTY

data class Expenses(
    var id: Long=0L,
    val title: String= String.EMPTY,
    val amount: Double =0.0,
    val category: String= String.EMPTY,
    val notes: String= String.EMPTY,
    val receipt: String= String.EMPTY,
    var date: String= String.EMPTY,
    var time: String= String.EMPTY,
)
