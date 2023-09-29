package com.example.budgetwise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Month

@Entity
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val budgetId: Int,

    @ColumnInfo(name = "budget")
    val budget: Float,

    @ColumnInfo(name = "month")
    val month: Month,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "spending_type")
    val spendingType: String,
)
