package com.example.budgetwise.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetwise.BudgetRecordViewModel
import com.example.budgetwise.ui.AddRecordButton
import com.example.budgetwise.ui.BudgetCard
import com.example.budgetwise.ui.theme.BudgetwiseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: BudgetRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BudgetwiseTheme{
                Surface(modifier = Modifier.fillMaxSize()) {
                    BudgetCard(
                        viewModel = viewModel
                    )
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        AddRecordButton { record,  ->
                            viewModel.addNewRecord(record)
                        }
                    }
                }
            }
        }
    }
}



