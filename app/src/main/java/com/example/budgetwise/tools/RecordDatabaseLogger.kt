package com.example.budgetwise.tools

import android.util.Log
import com.example.budgetwise.database.BudgetEntity
import com.example.budgetwise.database.DBRepository
import com.example.budgetwise.database.RecordEntity
import kotlinx.coroutines.flow.Flow


class RecordDatabaseLogger(private val dbRepository: DBRepository): DatabaseLogger {
    override suspend fun logFullDatabase() {
        logTableBudgets()
        logTableRecord()
    }

    private suspend fun logTableRecord() {
        val records: Flow<List<RecordEntity>> = dbRepository.getAllRecords()
        records.collect{
            for (record in it) {
                Log.d("DatabaseContents", "Records: $record")
            }
        }
    }

    private suspend fun logTableBudgets() {
        val budgets: Flow<List<BudgetEntity>> = dbRepository.getAllBudgets()
        budgets.collect{
            for (budget in it) {
                Log.d("DatabaseContents", "Budgets: $budget")
            }
        }
    }
}

interface DatabaseLogger {
    suspend fun logFullDatabase()
}