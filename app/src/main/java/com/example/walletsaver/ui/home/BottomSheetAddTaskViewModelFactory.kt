package com.example.walletsaver.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.TaskDatabaseDao
import java.lang.IllegalArgumentException

class BottomSheetAddTaskViewModelFactory(private val database: TaskDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomSheetAddTaskViewModel::class.java)) {
            return BottomSheetAddTaskViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}