package com.example.budgetwise.model

import com.example.budgetwise.database.BudgetEntity
import com.example.budgetwise.database.RecordEntity
import java.lang.Exception
import java.time.Month

data class Record(
    val spendingType: String,
    val spent: Float,
    val budget: Float,
    val month: Month,
    val year: Int,
)

fun RecordEntity.transformToRecord(budget: BudgetEntity): Record {
    if(this.budgetId != budget.budgetId)
        throw Exception("Tried to merge unrelated record and budget")
    return Record(
        spendingType = budget.spendingType,
        spent = this.spent,
        budget = budget.budget,
        month = budget.month,
        year = budget.year
    )
}