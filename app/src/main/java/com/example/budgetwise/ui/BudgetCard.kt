package com.example.budgetwise.ui

import androidx.compose.foundation.lazy.LazyColumn


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetwise.BudgetRecordViewModel
import com.example.budgetwise.R
import com.example.budgetwise.ui.states.BudgetCardUiState
import com.example.budgetwise.ui.states.SpendingCategory
import com.example.budgetwise.ui.states.SpendingType
import com.example.budgetwise.ui.states.budgetCardUiStateMock
import com.example.budgetwise.ui.states.spendingPeriodMock
import java.lang.Exception

@Preview
@Composable
fun BudgetCard(
    viewModel: BudgetRecordViewModel = hiltViewModel()
) {
    val lineDividerPainter = painterResource(id = R.drawable.divider)
    val uiState = viewModel.budgetCardUiState.collectAsState()

    Box(
        modifier = Modifier
            .padding(vertical = 24.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 23.dp, vertical = 10.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(modifier = Modifier.padding(vertical = 24.dp)) {
                BudgetCardPeriodDropdown(
                    spendingPeriod = uiState.value.spendingPeriod
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Headers: Spent, Available, Budget
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Spent")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "$${uiState.value.spent.getStringWithoutZero()}")
                    }

                    Image(
                        painter = lineDividerPainter,
                        contentDescription = null
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Available")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "$${uiState.value.available.getStringWithoutZero()}")

                    }

                    Image(
                        painter = lineDividerPainter,
                        contentDescription = null
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Budget")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "$${uiState.value.budget.getStringWithoutZero()}")
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                val progress = uiState.value.progress
                val progressValue = if(progress.isNaN()) 0f else progress

                LinearProgressIndicator(
                    progress = progressValue,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Gray,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 13.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                val spendingCategories = uiState.value.spendingCategories
                if(spendingCategories.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(spendingCategories) { category ->
                            SpendingCategoryListItem(category)
                        }
                    }
                } else {
                    DisplayEmptyScreen()
                }
            }
        }
    }
}

@Composable
fun DisplayEmptyScreen() {
    val imagePainter = painterResource(id = R.drawable.v567_n_46_doodles)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = imagePainter,
            contentDescription = "Empty list placeholder",
            contentScale = ContentScale.Crop,  // adjust this to your needs
            modifier = Modifier.size(200.dp, 200.dp)
        )
    }
}



