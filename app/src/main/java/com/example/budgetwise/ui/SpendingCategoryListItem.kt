package com.example.budgetwise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetwise.R
import com.example.budgetwise.ui.states.SpendingCategory
import java.lang.Exception


@Composable
fun SpendingCategoryListItem(category: SpendingCategory) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 13.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row {
                // Category icon
                Icon(
                    painter = painterResource(
                        id = category.spendingType.iconResId
                    ),
                    contentDescription = category.spendingType.title,

                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(41.dp)
                        .clip(CircleShape) // Clip it to a circle
                        .background(category.spendingType.color)
                        .padding(9.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    // Category name and description
                    Text(
                        text = category.spendingType.title
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Spent ${category.spent.getStringWithoutZero()}$ of ${category.budget.getStringWithoutZero()}$")
                }
            }

            // Amount left
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val text = category.left.getStringWithoutZero()
                Text(text = "$text$", style = TextStyle(fontSize = 24.sp))
                Text(text = "left")
            }
        }
        Surface(shadowElevation = 4.dp) {
            LinearProgressIndicator(
                progress = category.spent / category.budget,
                color = category.spendingType.color,
                trackColor = colorResource(id = R.color.dark_grey),
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

fun Float.getStringWithoutZero(): String {
    val text = if (this.rem(1.0) == 0.0) {
        this.toInt().toString()
    } else {
        this.toString()
    }
    return text
}