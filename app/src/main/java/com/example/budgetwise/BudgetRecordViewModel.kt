package com.example.budgetwise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwise.database.BudgetEntity
import com.example.budgetwise.database.DBRepository
import com.example.budgetwise.database.RecordEntity
import com.example.budgetwise.model.SpendingPeriod
import com.example.budgetwise.tools.DatabaseLogger
import com.example.budgetwise.tools.RecordDatabaseLogger
import com.example.budgetwise.ui.states.BudgetCardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Month
import javax.inject.Inject
import com.example.budgetwise.model.Record
import com.example.budgetwise.model.transformToRecord
import com.example.budgetwise.tools.getCurrentMonth
import com.example.budgetwise.tools.getCurrentYear
import com.example.budgetwise.ui.states.SpendingCategory
import com.example.budgetwise.ui.states.SpendingType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

@HiltViewModel
class BudgetRecordViewModel @Inject constructor(
    private val dbRepository: DBRepository
) : ViewModel() {
    private var _records: MutableStateFlow<List<Record>> = MutableStateFlow(listOf())

    val budgetCardUiState: StateFlow<BudgetCardUiState> get() = _uiState
    private val _uiState = MutableStateFlow(
        BudgetCardUiState(
            spendingPeriod = SpendingPeriod(
                month = getCurrentMonth(),
                year = getCurrentYear()
            ),
            spent = 0f,
            available = 0f,
            budget = 0f,
            spendingCategories = listOf()
        )
    )

    private val databaseLogger: DatabaseLogger

    init {
        initUiState()
        databaseLogger = RecordDatabaseLogger(dbRepository)
    }

    fun addNewRecord(record: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            var budget = dbRepository.getBudgetByDate(record.month, record.year, record.spendingType)
            if (budget == null) {
                val newBudget = BudgetEntity(
                    budgetId = 0,
                    budget = record.budget,
                    month = getCurrentMonth(),
                    year = getCurrentYear(),
                    spendingType = record.spendingType,
                )
                val budgetId = dbRepository.addBudget(newBudget)
                budget = newBudget.copy(budgetId = budgetId.toInt())
            }
            val recordEntity = RecordEntity(
                id = 0,
                spent = record.spent,
                budgetId = budget.budgetId
            )
            dbRepository.addRecord(recordEntity)
            databaseLogger.logFullDatabase()
        }.invokeOnCompletion {
            getRecordsForMonthAndYear(
                budgetCardUiState.value.spendingPeriod.month,
                budgetCardUiState.value.spendingPeriod.year,
                record.spendingType
            )
        }
    }

    private fun getRecordsForMonthAndYear(month: Month, year: Int, spendingType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val recordsList = dbRepository.getRecordsForBudgetByMonth(
                month = month,
                year = year
            )
            val budget = dbRepository.getBudgetByDate(
                month = month,
                year = year,
                spendingType = spendingType
            )
            budget?.let {
                val transformedList = recordsList.map {
                    it.transformToRecord(budget)
                }
                _records.value += transformedList
            }
        }
    }

    private fun initUiState() {
        viewModelScope.launch {
            val spendingPeriod = dbRepository.getLatestSpendingPeriod() ?: SpendingPeriod(
                getCurrentMonth(),
                getCurrentYear()
            )

            val spendingCategories = dbRepository.getSpendingCategoriesForSpendingPeriod(
                spendingPeriod
            )

            spendingCategories.forEach { spendingCategory ->
                getRecordsForMonthAndYear(
                    spendingPeriod.month,
                    spendingPeriod.year,
                    spendingCategory.spendingType.title
                )
            }

            //maybe needs flow to update in timely manner?
            generateBudgetCardUiState(spendingPeriod)

            //we have records[] so what to do then
            _records.collect { listOfRecords ->
                    val month = budgetCardUiState.value.spendingPeriod.month
                    val year = budgetCardUiState.value.spendingPeriod.year
                    val spendingPeriod = SpendingPeriod(month, year)
                    val budget = 0f

                    val spent: Float = listOfRecords.map { it.spent }.sum()
                    val availableBudget = if (budget - spent >= 0) spent / budget else 1f
                    val spendingCategories: List<SpendingCategory> = listOfRecords.groupBy { it.spendingType }
                        .map { recordsByType ->
                            SpendingCategory(
                                spendingType =
                                when (recordsByType.key) {
                                    SpendingType.FOOD.name -> SpendingType.FOOD
                                    SpendingType.EDUCATION.name -> SpendingType.EDUCATION
                                    SpendingType.TRANSPORTATION.name -> SpendingType.TRANSPORTATION
                                    else -> SpendingType.SHOPPING
                                },
                                spent = recordsByType.value.sumOf { it.spent.toDouble() }.toFloat(),
                                budget = recordsByType.value.sumOf { it.budget.toDouble() }.toFloat()
                            )
                        }

                    _uiState.value = BudgetCardUiState(
                            spendingPeriod = spendingPeriod,
                            spent = spent,
                            available = availableBudget,
                            budget = budget,
                            spendingCategories = spendingCategories
                        )
            }
        }
    }

    private fun generateBudgetCardUiState(spendingPeriod: SpendingPeriod) {
        viewModelScope.launch {

            val newUiState = BudgetCardUiState(
                spendingPeriod = spendingPeriod,
                spent = dbRepository.getSpentMoneyForSpendingPeriod(spendingPeriod),
                available = dbRepository.getAvailableMoneyForSpendingPeriod(spendingPeriod),
                budget = dbRepository.getBudgetForSpendingPeriod(spendingPeriod),
                spendingCategories = dbRepository.getSpendingCategoriesForSpendingPeriod(
                    spendingPeriod
                )
            )
            _uiState.value = newUiState
        }
    }
}
