package com.example.walletsaver.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Task
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class TaskDetailViewModel(val database: TaskDatabaseDao, val budgetId: Long) : ViewModel() {

    val budget = database.getBudget(budgetId)

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun updatePriority(priority: String) {
        var iconIndex = 0
        if (priority == "Urgent") {
            iconIndex = 1
        } else if (priority == "High") {
            iconIndex = 2
        }
        else if (priority == "Medium") {
            iconIndex = 3
        }
        else if (priority == "Low") {
            iconIndex = 4
        }

        val prep = budget.value
        uiScope.launch {
            update(prep?.let {
                Task(budgetId = it.budgetId, task = it.task, priority = priority ?: it.priority,
                    tag = it.tag, dueDate = it.dueDate, iconIndex = iconIndex ?: it.iconIndex,  creationDate = it.creationDate)
            })
        }
    }

    fun updateDueDate(dueDate: String) {
        val prep = budget.value
        uiScope.launch {
            update(prep?.let {
                Task(budgetId = it.budgetId, task = it.task, priority = it.priority,
                    tag = it.tag, dueDate = dueDate?: it.dueDate, iconIndex = it.iconIndex,  creationDate = it.creationDate)
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
