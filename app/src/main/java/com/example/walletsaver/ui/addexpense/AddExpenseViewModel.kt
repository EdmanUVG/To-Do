package com.example.walletsaver.ui.addexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Budget
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class AddExpenseViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val expense = MutableLiveData<String>()

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateBudget(category: String) {
        uiScope.launch {
            expense.value?.let { update(it, category) }
        }
    }

    private suspend fun update(expense: String, category: String) {
        withContext(Dispatchers.IO) {
                database.updateBudget(expense, category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
