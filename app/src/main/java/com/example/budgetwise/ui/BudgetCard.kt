package com.example.budgetwise.ui

import androidx.compose.foundation.lazy.LazyColumn


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetwise.BudgetRecordViewModel
import com.example.budgetwise.R


@Preview
@Composable
fun BudgetCard(
    uiState: BudgetCardUiState = budgetCardUiStateMock,
    viewModel: BudgetRecordViewModel = hiltViewModel()
) {
    val lineDividerPainter = painterResource(id = R.drawable.divider)

    Box(
        modifier = Modifier.wrapContentHeight()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                MonthsDropdown(
                    spendingPeriod = uiState.spendingPeriod
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Headers: Spent, Available, Budget
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Spent")
                    Image(
                        painter = lineDividerPainter,
                        contentDescription = null
                    )
                    Text(text = "Available")

                    Image(
                        painter = lineDividerPainter,
                        contentDescription = null
                    )
                    Text(text = "Budget")
                }

                // Values: Spending, Available and Budget
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$${uiState.spent}")
                    Text(text = "$${uiState.available}")
                    Text(text = "$${uiState.budget}")
                }

                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = uiState.progress,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Gray,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.spendingCategories) { category ->
                        SpendingCategoryListItem(category)
                    }
                }
            }
        }
    }
}



