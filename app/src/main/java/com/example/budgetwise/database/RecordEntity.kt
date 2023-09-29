package com.example.budgetwise.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "spent")
    val spent: Float,

    @ColumnInfo(name = "budget_id")
    val budgetId: Int
)


