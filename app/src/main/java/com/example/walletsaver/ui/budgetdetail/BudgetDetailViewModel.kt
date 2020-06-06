package com.example.walletsaver.ui.budgetdetail

import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class BudgetDetailViewModel(val database: BudgetDatabaseDao, val budgetId: Long) : ViewModel() {

    val budget = database.getBudget(budgetId)

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun deleteBudget() {
        uiScope.launch {
            delete()
        }
    }

    private suspend fun delete() {
        withContext(Dispatchers.IO) {
            budget.value?.let { database.delete(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
