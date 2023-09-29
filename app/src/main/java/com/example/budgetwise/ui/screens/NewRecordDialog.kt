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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetwise.tools.getCurrentMonth
import com.example.budgetwise.tools.getCurrentYear
import com.example.budgetwise.ui.reusable_elements.CreateGenericDropdown
import com.example.budgetwise.ui.reusable_elements.MyDatePickerDialog
import com.example.budgetwise.ui.states.SpendingType
import com.example.budgetwise.model.Record
import java.time.Month
import androidx.compose.runtime.getValue

data class DateRecord(val year: Int, val month: Month)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSpendingDialog(
    isDialogOpen: Boolean,
    onDialogClose: (Record?) -> Unit
) {
    if (isDialogOpen) {
        val selectedCategoryState = remember { mutableStateOf(SpendingType.FOOD) }
        val spentAmountText = remember { mutableStateOf("0") }
        var recordDate by remember {
            mutableStateOf(
                DateRecord(getCurrentYear(), getCurrentMonth())
            )
        }

        fun getSpentAmountFloat(): Float =
            spentAmountText.value.toFloatOrNull() ?: throw Exception()

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

                CreateGenericDropdown(
                    label = "Spending category",
                    names = SpendingType.values().toSet(),
                    selectedValueIndex = 0
                )
                Spacer(modifier = Modifier.height(8.dp))
                AmountInput(spentAmountText) {
                    spentAmountText.value = it
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
                        val record = Record(
                            spendingType = selectedCategoryState.value.name,
                            spent = getSpentAmountFloat(),
                            budget = 0f, // TODO implement adding budget
                            month = getDate().month,
                            year = getDate().year,
                        )
                        onDialogClose(record)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun AmountInput(amountText: MutableState<String>, onTextChanged: (String) -> Unit) {
    TextField(
        value = amountText.value,
        onValueChange = onTextChanged,
        label = { Text("Amount in dollars") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
