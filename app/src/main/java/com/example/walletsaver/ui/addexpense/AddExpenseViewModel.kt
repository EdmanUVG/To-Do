package com.example.walletsaver.ui.addexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Budget
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class AddExpenseViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateBudget(expense: Int, category: String) {
        uiScope.launch {
            update(expense, category)
        }
    }

    private suspend fun update(expense: Int, category: String) {
        withContext(Dispatchers.IO) {
                database.updateBudget(expense, category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
