package com.example.walletsaver.ui.overview

import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class OverviewViewModel(val database: TaskDatabaseDao) : ViewModel() {

    val completedTasksCount = database.getCountCompletedTasks()

    val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
}