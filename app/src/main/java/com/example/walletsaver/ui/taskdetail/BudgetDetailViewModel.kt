package com.example.walletsaver.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class BudgetDetailViewModel(val database: TaskDatabaseDao, val budgetId: Long) : ViewModel() {

    val budget = database.getBudget(budgetId)

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _budgetClicked = MutableLiveData<Long>()
    val budgetClicked: LiveData<Long>
        get() = _budgetClicked

    fun onBudgetClicked(budgetId: Long) {
        _budgetClicked.value = budgetId
    }

    fun onBudgetClickedCompleted() {
        _budgetClicked.value = null
    }


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
