package com.example.budgetwise.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.budgetwise.R

@Composable
fun BudgetwiseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = colorResource(id = R.color.purple),
            onPrimary = colorResource(id = R.color.white),
            secondary = colorResource(id = R.color.green),
            onSecondary = Color.Black,
            surface = colorResource(id = R.color.light_blue),
            primaryContainer = colorResource(id = R.color.white)
        ),
        typography = Typography,
        content = content,
    )
}
