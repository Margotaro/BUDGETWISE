package com.example.budgetwise.ui

import androidx.compose.ui.graphics.Color
import com.example.budgetwise.R
import com.example.budgetwise.model.SpendingPeriod

class BudgetCardUiState(
    var spendingPeriod: SpendingPeriod,
    var spent: Float,
    var available: Float,
    var budget: Float,
    var spendingCategories: List<SpendingCategory>
) {
    var progress: Float
        get() = spent / budget
        private set(value) {}
}

data class SpendingCategory(
    val spendingType: SpendingType,
    var spent: Float,
    var budget: Float
) {
    var left: Float
        get() = budget - spent
        private set(value) {}
}

enum class SpendingType(
    val iconResId: Int,
    val title: String,
    val color: Color
) {
    FOOD(R.drawable.food_icon, "Food", Color(0xFF213B80)),
    SHOPPING(R.drawable.shopping_icon, "Shopping", Color(0xFF386BBC)),
    TRANSPORTATION(R.drawable.transportation_icon, "Transportation", Color(0xFFFFB900)),
    EDUCATION(R.drawable.education_icon, "Education", Color(0xFF46BDC6))
}