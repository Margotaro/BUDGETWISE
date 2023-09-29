package com.example.budgetwise.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Month

@Dao
interface RecordDAO {
    @Query("SELECT * FROM RecordEntity")
    fun getAllRecords(): Flow<List<RecordEntity>>
    @Query("SELECT * FROM BudgetEntity")
    fun getAllBudgets(): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM BudgetEntity WHERE month = :month AND year = :year")
    suspend fun getBudgetsForMonthAndYear(month: Month, year: Int): List<BudgetEntity>

    @Transaction
    suspend fun getRecordsForBudget(month: Month, year: Int): List<BudgetWithRecords> {
        val budgets = getBudgetsForMonthAndYear(month, year)
        return budgets.map { budget ->
            BudgetWithRecords(budget, getRecordsForBudgetId(budget.budgetId))
        }
    }

    @Query("SELECT * FROM RecordEntity WHERE budget_id = :budgetId")
    suspend fun getRecordsForBudgetId(budgetId: Int): List<RecordEntity>

    @Insert
    suspend fun addRecord(newRecordEntity: RecordEntity)

    @Delete
    suspend fun deleteRecord(newRecordEntity: RecordEntity)
    @Query("SELECT * FROM BudgetEntity ORDER BY year DESC, month DESC LIMIT 1")
    suspend fun getNewestBudgetRecord(): BudgetEntity?


    //TODO: "spent" field isn't updated but rather counted distinctively.
    @Query(
        """
    SELECT b.spending_type, r.spent, b.budget
    FROM RecordEntity r
    JOIN BudgetEntity b ON r.budget_id = b.budgetId
    WHERE b.year = :year AND b.month = :month
    """
    )
    suspend fun getDistinctSpendingTypesByYearAndMonth(year: Int, month: Month): List<SpendingTypeData>
    @Query("SELECT b.budget FROM BudgetEntity b WHERE b.year = :year AND b.month = :month")
    suspend fun getBudgetForSpendingPeriod(year: Int, month: Month): List<Float>
    @Query(
        """
    SELECT b.budget - r.spent
    FROM RecordEntity r
    JOIN BudgetEntity b ON r.budget_id = b.budgetId
    WHERE b.year = :year AND b.month = :month
    """
    )
    suspend fun getAvailableMoneyForSpendingPeriod(year: Int, month: Month): List<Float>
    @Query(
        """
    SELECT r.spent
    FROM RecordEntity r
    JOIN BudgetEntity b ON r.budget_id = b.budgetId
    WHERE b.year = :year AND b.month = :month
    """
    )
    suspend fun getSpentMoneyForSpendingPeriod(year: Int, month: Month): List<Float>
    @Query("SELECT * FROM BudgetEntity WHERE year = :year AND month = :month AND spending_type = :spending_type LIMIT 1")
    suspend fun getBudgetByDate(year: Int, month: Month, spending_type: String): BudgetEntity?

    @Insert
    fun addBudget(budgetEntity: BudgetEntity): Long
}

data class SpendingTypeData(
    val spending_type: String,
    val spent: Float,
    val budget: Float
)

