package com.example.budgetwise.ui

import com.example.budgetwise.model.Month
import com.example.budgetwise.model.SpendingPeriod
import com.example.budgetwise.model.Year

val budgetCardUiStateMock : BudgetCardUiState = BudgetCardUiState(
    available = 1200f,
    budget = 2000f,
    spent = 800f,
    spendingPeriod = SpendingPeriod(Month.APRIL, Year(2023)),
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

val spendingPeriodMock : SpendingPeriod = SpendingPeriod(
    Month.APRIL,
    Year(2023)
)