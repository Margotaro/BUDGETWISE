package com.example.budgetwise.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction


@Dao
interface RecordDAO {
    @Query("SELECT * FROM record")
    fun getAllRecords(): MutableLiveData<List<Record>>

    @Transaction
    @Query("SELECT * FROM BudgetOfRecord WHERE budgetId IN (SELECT budgetId FROM Budget WHERE month = :month AND year = :year)")
    fun getRecordsForBudget(month: String, year: Int): MutableLiveData<List<BudgetWithRecords>>

    @Insert
    fun addRecord(newRecord: Record)

    @Delete
    fun deleteRecord(newRecord: Record)
}

data class BudgetWithRecords(
    @Embedded
    val budget: Budget,

    @Relation(
        parentColumn = "id",
        entityColumn = "budgetId"
    )
    val records: List<Record>
)
