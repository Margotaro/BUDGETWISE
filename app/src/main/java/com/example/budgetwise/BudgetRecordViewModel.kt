package com.example.budgetwise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwise.database.Record
import com.example.budgetwise.database.RecordDatabase
import com.example.budgetwise.model.Month
import com.example.budgetwise.model.SpendingPeriod
import com.example.budgetwise.model.Year
import com.example.budgetwise.ui.BudgetCardUiState
import com.example.budgetwise.ui.SpendingCategory
import com.example.budgetwise.ui.SpendingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BudgetRecordViewModel @Inject constructor(appDatabase: RecordDatabase) : ViewModel() {
    private val userDao = appDatabase.recordDao()
    private lateinit var _records: MutableLiveData<List<Record>>
    val uiState: LiveData<BudgetCardUiState> get() = _uiState
    private val _uiState = MutableLiveData<BudgetCardUiState>()

    val records: LiveData<List<Record>>
        get() = _records

    fun getRecordsForMonthAndYear(month: String, year: Int) {
        viewModelScope.launch {
            val records = getRecordsFromBudgetWithRecords(month, year)
            processRecords(records)
        }
    }


    fun initUiState(): MutableLiveData<BudgetCardUiState> {
        val uiState = MutableLiveData<BudgetCardUiState>()
        val month = Month.AUGUST
        val year = Year(2023)
        var spendingPeriod = SpendingPeriod(month, year)
        var spent: Float
        var availableBudget: Float
        var budget = 0f
        var spendingCategories: List<SpendingCategory>

        getRecordsForMonthAndYear(month.name, year.year)
        _uiState.observe(_records) { records ->

            spent = records.map { it.spent }.sum()
            availableBudget = if (budget - spent >= 0) spent / budget else 1f
            spendingCategories = records.groupBy { it.spendingType }
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
                        budget = recordsByType.value.sumOf { it.budgetId.toDouble() }.toFloat()
                    )
                }

            uiState.postValue(
                BudgetCardUiState(
                    spendingPeriod = spendingPeriod,
                    spent = spent,
                    available = availableBudget,
                    budget = budget,
                    spendingCategories = spendingCategories
                )
            )

        }
        return uiState
    }

    private fun getRecordsFromBudgetWithRecords(month: String, year: Int): MutableLiveData<List<Record>> {
        val budgetWithRecordsList = userDao.getRecordsForBudget(month, year)
        return MutableLiveData(budgetWithRecordsList.value?.flatMap { budgetWithRecords -> budgetWithRecords.records })
    }

    private fun processRecords(records: MutableLiveData<List<Record>>) {
        _records = records
    }

}