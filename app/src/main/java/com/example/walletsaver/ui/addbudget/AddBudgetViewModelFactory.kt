package com.example.walletsaver.ui.addbudget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.BudgetDatabaseDao

class AddBudgetViewModelFactory(private val database: BudgetDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddBudgetViewModel::class.java)) {
            return AddBudgetViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}