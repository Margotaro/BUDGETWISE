package com.example.budgetwise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "spending_type")
    val spendingType: String,

    @ColumnInfo(name = "spent")
    val spent: Float,

    @ColumnInfo(name = "budget_id")
    val budgetId: Int
)


