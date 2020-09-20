package com.example.walletsaver.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Task
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class HomeViewModel(val database: TaskDatabaseDao) : ViewModel() {

    val tasks = database.getTasks()
    val tasksByPriority = database.getTasksByPriority()

    val rowsCount = database.getRowsCount()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _budgetClicked = MutableLiveData<Long>()
    val budgetClicked: LiveData<Long>
    get() = _budgetClicked


    private var _isSorted = MutableLiveData<Boolean>()
    val isSorted: LiveData<Boolean>
    get() = _isSorted

    init {
        _isSorted.value = false
    }

    fun onNoPriorityClicked() {
        _isSorted.value = false
    }

    fun onPriorityClicked() {
        _isSorted.value = true
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