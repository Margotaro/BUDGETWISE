package com.example.budgetwise.ui.states

import androidx.compose.ui.graphics.Color
import com.example.budgetwise.R
import com.example.budgetwise.model.INamed
import com.example.budgetwise.model.SpendingPeriod
import java.lang.Exception

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
    val title: String,
    val color: Color = Color.Transparent,
    val iconResId: Int = -1
): INamed {
    FOOD( "FOOD", Color(0xFF213B80), R.drawable.food_icon),
    SHOPPING( "SHOPPING", Color(0xFF386BBC), R.drawable.shopping_icon),
    TRANSPORTATION( "TRANSPORTATION", Color(0xFFFFB900), R.drawable.transportation_icon),
    EDUCATION( "EDUCATION", Color(0xFF46BDC6), R.drawable.education_icon);

    override fun getName(): String {
        return title
    }

    companion object {
        fun createSpendingTypeByTitle(title: String): SpendingType {
            when(title) {
                FOOD.title -> return FOOD
                SHOPPING.title -> return SHOPPING
                TRANSPORTATION.title -> return TRANSPORTATION
                EDUCATION.title -> return EDUCATION
            }
            throw Exception("Unknown spending title, unable to create SpendingType instance")
        }
    }
}