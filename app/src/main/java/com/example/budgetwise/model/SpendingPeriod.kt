package com.example.budgetwise.model

import java.util.Calendar


class SpendingPeriod(val month: Month, val year: Year)

enum class Month(name: String) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December")
}

data class Year(val year: Int) {
    init {
        val currentYear = getCurrentYear()
        require(year in 2023..currentYear) {
            "Year must be greater than or equal to 2023 and less than or equal to the current year."
        }
    }

    private fun getCurrentYear(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
}