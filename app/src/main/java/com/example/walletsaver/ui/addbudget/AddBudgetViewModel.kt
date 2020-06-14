package com.example.walletsaver.ui.addbudget

import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Budget
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class AddBudgetViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertBudget(budget: Int, category: String, iconIndex: Int) {
        uiScope.launch {
            insert(budget, category, iconIndex)
        }
    }

    private suspend fun insert(budget: Int, category: String, iconIndex: Int) {
        withContext(Dispatchers.IO) {
            database.insert(Budget(budget = budget?: 0, category = category?:"", iconIndex = iconIndex?: 2,
            income = 0, expense = 0))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
