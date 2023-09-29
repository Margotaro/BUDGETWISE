package com.example.budgetwise.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.budgetwise.ui.screens.ShowSpendingDialog
import com.example.budgetwise.model.Record

@Composable
fun AddRecordButton(onClick: (Record) -> Unit) {
    val showDialog = remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = {
            showDialog.value = true
        },
        containerColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .height(56.dp)
            .width(56.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = Color.White,
        )
        if(showDialog.value)
            ShowSpendingDialog(true) { record ->
                showDialog.value = false
                record?.let {
                    onClick(record)
                }
            }
        else
            ShowSpendingDialog(false) {
                showDialog.value = false
            }
    }
}