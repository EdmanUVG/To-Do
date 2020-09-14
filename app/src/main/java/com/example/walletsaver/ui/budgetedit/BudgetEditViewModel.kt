package com.example.walletsaver.ui.budgetedit


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Task
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class BudgetEditViewModel(val database: TaskDatabaseDao, val budgetId: Long) : ViewModel() {

    val budget = database.getBudget(budgetId)

    val amount = MutableLiveData<String>()

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateBudget(amount: String) {
        val prep = budget.value
        uiScope.launch {
            update(prep?.let {
                Task(budgetId = it.budgetId, tag = it.tag, task = amount ?: it.task,
                iconIndex = it.iconIndex, income = it.income, expense = it.expense)
            })
        }
    }

    private suspend fun update(task: Task?) {
        withContext(Dispatchers.IO) {
            task?.let {
                database.update(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
