package com.example.budgetwise.tools

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Month

fun getCurrentMonth(): Month {
    val currentMoment: Instant = Clock.System.now()
    return currentMoment
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .month
}

fun getCurrentYear(): Int {
    val currentMoment: Instant = Clock.System.now()
    return currentMoment
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .year
}