package com.example.walletsaver.ui.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.TaskDatabaseDao
import java.lang.IllegalArgumentException

class BottomSheetAddSubTaskViewModelFactory(private val database: TaskDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BottomSheetAddSubTaskViewModel::class.java)) {
            return BottomSheetAddSubTaskViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}