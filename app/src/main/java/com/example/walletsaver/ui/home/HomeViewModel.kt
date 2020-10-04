package com.example.walletsaver.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class HomeViewModel(val database: TaskDatabaseDao) : ViewModel() {

    val tasks = database.getTasks()
    val completedTasks = database.getTaskCompleted()
    val tasksByPriority = database.getTaskByPriority()
    val taskByDate = database.getTaskByDate()

    val rowsCount = database.getRowsCount()
    val completedTasksCount = database.getCountCompletedTasks()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _budgetClicked = MutableLiveData<Long>()
    val budgetClicked: LiveData<Long>
    get() = _budgetClicked

    // 0 = No order
    // 1 = Priority
    // 2 = Date

    private var _isSorted = MutableLiveData<Int>()
    val isSorted: LiveData<Int>
    get() = _isSorted

    init {
        _isSorted.value = 0
    }

    fun onNoPriorityClicked() {
        _isSorted.value = 0
    }

    fun onPriorityClicked() {
        _isSorted.value = 1
    }

    fun onDateClicked() {
        _isSorted.value = 2
    }


    fun onBudgetClicked(budgetId: Long) {
        _budgetClicked.value = budgetId
    }

    fun onBudgetClickedCompleted() {
        _budgetClicked.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}