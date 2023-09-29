package com.example.budgetwise.ui.reusable_elements

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.budgetwise.ui.screens.DateRecord
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun MyDatePickerDialog(
    onDateSelected: (DateRecord) -> Unit,
    onDismiss: (DialogInterface) -> Unit
) {

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val todayInMillis = Clock.System.now().toEpochMilliseconds()
    val year = today.year
    val month = today.month.ordinal
    val dayOfMonth = today.dayOfMonth

    var selectedDateText by remember { mutableStateOf(
        getTextOutOfDate(
            dayOfMonth,
            month,
            year)
        )
    }

    val datePicker = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = getTextOutOfDate(
                selectedDayOfMonth,
                selectedMonth,
                selectedYear
            )
        }, year, month, dayOfMonth
    )
    datePicker.datePicker.minDate = todayInMillis
    datePicker.setOnDateSetListener { datePicker, year, month, day ->
        onDateSelected(
            DateRecord(year, Month(month))
        )
    }
    datePicker.setOnDismissListener(onDismiss)

    Row(
        modifier = Modifier.wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Selected date is\n $selectedDateText",
            color = MaterialTheme.colorScheme.onSecondary
        )

        Button(
            onClick = {
                datePicker.show()
            }
        ) {
            Text(text = "Select a date")
        }
    }
}

fun getTextOutOfDate(selectedDayOfMonth: Int, selectedMonth: Int, selectedYear: Int): String {
    return "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
}