package com.example.budgetwise.database

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetWithRecords(
    @Embedded
    val budgetEntity: BudgetEntity,

    @Relation(
        parentColumn = "budgetId",
        entityColumn = "budgetId"
    )
    val recordEntities: List<RecordEntity>
)