package com.example.walletsaver.ui.addincome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.TaskDatabaseDao

class AddIncomeViewModelFactory (private val database: TaskDatabaseDao): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddIncomeViewModel::class.java)) {
            return AddIncomeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}