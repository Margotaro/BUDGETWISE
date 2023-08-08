package com.example.budgetwise.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import com.example.budgetwise.BudgetRecordViewModel
import com.example.budgetwise.database.DBRepository
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
    val repository = DBRepository(appDatabase.recordDao())

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
        viewModel.uiState.observe(this, { uiState ->
            // Update the UI based on the UI state
        })

    }
}



