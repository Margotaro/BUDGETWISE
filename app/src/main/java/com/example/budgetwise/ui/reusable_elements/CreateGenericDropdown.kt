package com.example.budgetwise.ui.reusable_elements

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.budgetwise.model.INamed
import java.time.Month

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGenericDropdown(label: String, names: Set<INamed>, selectedValueIndex: Int) {

    var isExpanded by remember { mutableStateOf(false) }
    val selectedValueState =
        remember { mutableStateOf(names.elementAt(selectedValueIndex)) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        }
    ) {
        TextField(
            value = selectedValueState.value.getName(),
            onValueChange = { changedValue ->
                val selectedOption = names.firstOrNull { named ->
                    named.getName() == changedValue
                }

                selectedOption?.let {
                    selectedValueState.value = selectedOption
                }
            },
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            label = { Text(label) }
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            names.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedValueState.value = names.find { option ->
                            option.getName() == selectionOption.getName()
                        } ?: names.first()
                        isExpanded = false
                    },
                    text = { Text(text = selectionOption.getName()) }
                )
            }
        }
    }
}