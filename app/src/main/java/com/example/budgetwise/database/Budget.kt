package com.example.budgetwise.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Budget(
    @PrimaryKey
    val budgetId: Int,

    @ColumnInfo(name = "budget")
    val budget: Int,

    @ColumnInfo(name = "month")
    val month: String,

    @ColumnInfo(name = "year")
    val year: Int,
)

@Entity
data class BudgetOfRecord(
    @Embedded val budget: Budget,
    @Relation(
        parentColumn = "budgetId",
        entityColumn = "budgetId"
    )
    val records: List<Record>
)