package com.example.walletsaver.ui.budgetedit

import android.provider.SyncStateContract.Helpers.update
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Budget
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class BudgetEditViewModel(val database: BudgetDatabaseDao, val budgetId: Long) : ViewModel() {

    val budget = database.getBudget(budgetId)

    val amount = MutableLiveData<String>()

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateBudget() {
        val prep = budget.value
        uiScope.launch {
            update(prep?.let {
                Budget(budgetId = it.budgetId, category = it.category, amount = amount.value ?: it.amount,
                iconIndex = it.iconIndex, income = it.income, expense = it.expense)
            })
        }
    }

    private suspend fun update(budget: Budget?) {
        withContext(Dispatchers.IO) {
            budget?.let {
                database.update(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
