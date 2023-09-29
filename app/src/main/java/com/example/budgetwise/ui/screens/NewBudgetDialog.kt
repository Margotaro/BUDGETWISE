package com.example.budgetwise.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.budgetwise.database.BudgetEntity
import com.example.budgetwise.tools.getCurrentMonth
import com.example.budgetwise.tools.getCurrentYear
import com.example.budgetwise.ui.reusable_elements.CreateGenericDropdown
import com.example.budgetwise.ui.reusable_elements.MyDatePickerDialog
import com.example.budgetwise.ui.states.SpendingType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBudgetCreationDialog(
    isDialogOpen: Boolean,
    onDialogClose: (BudgetEntity?) -> Unit,
    spendingType: SpendingType = SpendingType.FOOD
) {
    if (isDialogOpen) {
        val selectedCategoryState = remember { mutableStateOf(spendingType) }
        val budgetAmountText = remember { mutableStateOf("0") }
        var recordDate by remember {
            mutableStateOf(
                DateRecord(getCurrentYear(), getCurrentMonth())
            )
        }

        fun getBudgetAmountFloat(): Float =
            budgetAmountText.value.toFloatOrNull() ?: throw Exception()

        fun getDate(): DateRecord = recordDate

        AlertDialog(
            onDismissRequest = { onDialogClose(null) },
            modifier = Modifier
                .shadow(20.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                MyDatePickerDialog({ dateRecord ->
                    recordDate = DateRecord(dateRecord.year, dateRecord.month)
                }, {
                    recordDate = DateRecord(getCurrentYear(), getCurrentMonth())
                })
                Spacer(modifier = Modifier.height(8.dp))

                val spendingTypes = SpendingType.values().toSet()
                CreateGenericDropdown(
                    label = "Spending category",
                    names = spendingTypes,
                    selectedValueIndex = spendingTypes.indexOf(spendingType)
                )
                Spacer(modifier = Modifier.height(8.dp))
                AmountInput(budgetAmountText) {
                    budgetAmountText.value = it
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 8.dp)
                ) {
                    Button(onClick = { onDialogClose(null) }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val budget = BudgetEntity(
                            budgetId = 0,
                            spendingType = selectedCategoryState.value.name,
                            budget = getBudgetAmountFloat(),
                            month = getDate().month,
                            year = getDate().year,
                        )
                        onDialogClose(budget)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}