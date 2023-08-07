package com.example.budgetwise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddRecordButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add padding from the bottom right
        contentAlignment = Alignment.BottomEnd // Place children to bottom right
    ) {
        Box(
            modifier = Modifier
                .size(38.dp) // Set the size
                .background(Color.Blue, shape = CircleShape) // Make it round and set the background color
                .clickable(onClick = onClick) // Set the click action
        ) {
            Text(
                text = "+",
                fontSize = 24.sp, // Font size
                fontWeight = FontWeight.Bold, // Font weight
                color = Color.White, // Font color
                modifier = Modifier.align(Alignment.Center) // Center the text inside the box
            )
        }
    }
}