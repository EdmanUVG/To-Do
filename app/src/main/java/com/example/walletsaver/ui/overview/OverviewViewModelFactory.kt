package com.example.walletsaver.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.TaskDatabaseDao

class OverviewViewModelFactory(private val database: TaskDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(database) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}