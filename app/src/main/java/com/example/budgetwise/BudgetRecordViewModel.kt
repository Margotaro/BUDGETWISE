package com.example.budgetwise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetwise.database.Record
import com.example.budgetwise.database.RecordDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BudgetRecordViewModel @Inject constructor(appDatabase: RecordDatabase) : ViewModel() {
    val userDao = appDatabase.recordDao()
    private lateinit var _records: MutableLiveData<List<Record>>
    val allRecords: LiveData<List<Record>>
        get() = _records

    fun getRecordsForMonthAndYear(month: String, year: Int) {
        viewModelScope.launch {
            val records = getRecordsFromBudgetWithRecords(month, year)
            processRecords(records)
        }
    }

    private suspend fun getRecordsFromBudgetWithRecords(month: String, year: Int): List<Record> {
        return withContext(Dispatchers.IO) {
            val budgetWithRecordsList = userDao.filterRecordsByMonth(month, year)
            budgetWithRecordsList.flatMap { budgetWithRecords -> budgetWithRecords.records }
        }
    }

    private fun processRecords(records: List<Record>) {
        _records.postValue(records)
    }

}