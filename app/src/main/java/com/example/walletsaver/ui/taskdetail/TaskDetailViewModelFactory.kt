package com.example.walletsaver.ui.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.TaskDatabaseDao

class TaskDetailViewModelFactory(private val database: TaskDatabaseDao,
                                 private val budgetId: Long): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
            return TaskDetailViewModel(database, budgetId) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}