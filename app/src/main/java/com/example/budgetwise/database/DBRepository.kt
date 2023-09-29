package com.example.budgetwise.database

import com.example.budgetwise.tools.getCurrentMonth
import com.example.budgetwise.tools.getCurrentYear
import com.example.budgetwise.model.SpendingPeriod
import com.example.budgetwise.ui.states.SpendingCategory
import com.example.budgetwise.ui.states.SpendingType
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Month
import javax.inject.Inject

class DBRepository @Inject constructor(private val myDao: RecordDAO) {
    suspend fun addRecord(recordEntity: RecordEntity) {
        myDao.addRecord(recordEntity)
    }

    fun getAllRecords(): Flow<List<RecordEntity>> {
        return myDao.getAllRecords()
    }

    fun getAllBudgets(): Flow<List<BudgetEntity>> {
        return myDao.getAllBudgets()
    }

    suspend fun getBudgetByDate(month: Month, year: Int, spendingType: String): BudgetEntity? {
        return myDao.getBudgetByDate(year, month, spendingType)
    }
    suspend fun getRecordsForBudgetByMonth(month: Month, year: Int): List<RecordEntity> {
        return myDao.getRecordsForBudget(month, year).flatMap { budgetWithRecords -> budgetWithRecords.recordEntities }
    }

    suspend fun getLatestSpendingPeriod(): SpendingPeriod? {
        val budget = myDao.getNewestBudgetRecord()
        budget?.let {
            return SpendingPeriod(budget.month ?: getCurrentMonth(), budget.year ?: getCurrentYear())
        }
        return null
    }

    suspend fun getSpendingCategoriesForSpendingPeriod(spendingPeriod: SpendingPeriod): List<SpendingCategory> {
        val spendingPeriodStrings = myDao.getDistinctSpendingTypesByYearAndMonth(
            spendingPeriod.year,
            spendingPeriod.month
        )
        return spendingPeriodStrings.map {
            SpendingCategory(
                spendingType = SpendingType.createSpendingTypeByTitle(it.spending_type),
                spent = it.spent,
                budget = it.budget
            )
        }
    }

    suspend fun getBudgetForSpendingPeriod(spendingPeriod: SpendingPeriod): Float {
        return myDao.getBudgetForSpendingPeriod(spendingPeriod.year, spendingPeriod.month).firstOrNull()?.toFloat() ?: 0f
    }

    suspend fun getAvailableMoneyForSpendingPeriod(spendingPeriod: SpendingPeriod): Float {
        return myDao.getAvailableMoneyForSpendingPeriod(spendingPeriod.year, spendingPeriod.month).firstOrNull()?.toFloat() ?: 0f
    }

    suspend fun getSpentMoneyForSpendingPeriod(spendingPeriod: SpendingPeriod): Float {
        return myDao.getSpentMoneyForSpendingPeriod(spendingPeriod.year, spendingPeriod.month).firstOrNull()?.toFloat() ?: 0f
    }

    fun addBudget(budgetEntity: BudgetEntity): Long {
        return myDao.addBudget(budgetEntity)
    }
}