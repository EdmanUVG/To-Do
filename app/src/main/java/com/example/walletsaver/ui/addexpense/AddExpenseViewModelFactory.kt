package com.example.walletsaver.ui.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.BudgetDatabaseDao
import com.example.walletsaver.ui.addbudget.AddBudgetViewModel

class AddExpenseViewModelFactory(private val database: BudgetDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}