package com.example.budgetwise.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecordEntity::class, BudgetEntity::class], version = 1)
abstract class RecordDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDAO
}