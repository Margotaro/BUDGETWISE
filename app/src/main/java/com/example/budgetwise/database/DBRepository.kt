package com.example.budgetwise.database

import androidx.lifecycle.MutableLiveData
import com.example.budgetwise.model.Month
import com.example.budgetwise.model.Year

class DBRepository(private val myDao: RecordDAO) {
    fun getRecordsByMonth(month: Month, year: Year): MutableLiveData<List<BudgetWithRecords>> {
        return myDao.getRecordsForBudget(month.name, year.year)
    }
}