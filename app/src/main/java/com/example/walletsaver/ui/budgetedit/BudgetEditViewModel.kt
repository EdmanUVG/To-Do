package com.example.walletsaver.ui.budgetedit

import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class BudgetEditViewModel(val database: BudgetDatabaseDao, val budgetId: Long) : ViewModel() {

    val budget = database.getBudget(budgetId)

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
