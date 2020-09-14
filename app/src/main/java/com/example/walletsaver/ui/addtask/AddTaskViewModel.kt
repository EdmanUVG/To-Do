package com.example.walletsaver.ui.addtask

import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Task
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class AddTaskViewModel(val database: TaskDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertBudget(budget: String, category: String, iconIndex: Int) {
        uiScope.launch {
            insert(budget, category, iconIndex)
        }
    }

    private suspend fun insert(budget: String, category: String, iconIndex: Int) {
        withContext(Dispatchers.IO) {
            database.insert(Task(task = budget?: "", tag = category?:"", iconIndex = iconIndex?: 2,
            income = 0, expense = 0))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
