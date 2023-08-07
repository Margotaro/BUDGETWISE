package com.example.budgetwise.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.budgetwise.BudgetRecordViewModel
import com.example.budgetwise.database.Record
import com.example.budgetwise.database.RecordDatabase
import com.example.budgetwise.model.Month
import com.example.budgetwise.model.SpendingPeriod
import com.example.budgetwise.model.Year
import com.example.budgetwise.ui.AddRecordButton
import com.example.budgetwise.ui.BudgetCard
import com.example.budgetwise.ui.BudgetCardUiState
import com.example.budgetwise.ui.SpendingCategory
import com.example.budgetwise.ui.SpendingType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appDatabase: RecordDatabase

    @Inject
    lateinit var viewModel: BudgetRecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                BudgetCard()
                AddRecordButton {}
            }
        }

    }
    fun initUiState(): MutableLiveData<BudgetCardUiState> {
        val uiState = MutableLiveData<BudgetCardUiState>()
        val month = Month.AUGUST
        val year = Year(2023)
        var spendingPeriod = SpendingPeriod(month, year)
        var spent = 0f
        var availableBudget = 0f
        var budget = 0f
        var spendingCategories = listOf<SpendingCategory>()
        viewModel.getRecordsForMonthAndYear(month.name, year.year)
        viewModel.allRecords.observe(this@MainActivity) { records ->
            spent = records.map { it.spent }.sum()
            availableBudget = if(budget - spent >= 0) spent/budget else 1f
            spendingCategories = records.map { record -> SpendingCategory(
                spendingType =
                    when (record.spendingType) {
                        SpendingType.FOOD.name -> SpendingType.FOOD
                        SpendingType.EDUCATION.name -> SpendingType.EDUCATION
                        SpendingType.TRANSPORTATION.name -> SpendingType.TRANSPORTATION
                        else -> SpendingType.SHOPPING
                    }
                ,
                spent = record.spent,
                budget = record.bu
            ) }
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
}



