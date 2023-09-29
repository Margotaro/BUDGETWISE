package com.example.budgetwise.ui.states

import com.example.budgetwise.model.SpendingPeriod
import kotlinx.datetime.Month

val spendingPeriodMock : SpendingPeriod = SpendingPeriod(
    Month.APRIL,
    2023
)

val budgetCardUiStateMock : BudgetCardUiState = BudgetCardUiState(
    available = 1200f,
    budget = 2000f,
    spent = 800f,
    spendingPeriod = SpendingPeriod(spendingPeriodMock.month, spendingPeriodMock.year),
    spendingCategories = listOf(
        SpendingCategory(
            spendingType = SpendingType.FOOD,
            budget = 100f,
            spent = 10f
        ),
        SpendingCategory(
            spendingType = SpendingType.SHOPPING,
            budget = 100f,
            spent = 20f
        ),
        SpendingCategory(
            spendingType = SpendingType.TRANSPORTATION,
            budget = 100f,
            spent = 20f
        ),
        SpendingCategory(
            spendingType = SpendingType.EDUCATION,
            budget = 100f,
            spent = 40f
        )
    )
)
