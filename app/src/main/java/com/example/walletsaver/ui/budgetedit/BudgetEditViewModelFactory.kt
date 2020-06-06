package com.example.walletsaver.ui.budgetedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.BudgetDatabaseDao

class BudgetEditViewModelFactory (private val database: BudgetDatabaseDao,
                                  private val budgetId: Long): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BudgetEditViewModel::class.java)) {
            return BudgetEditViewModel(database, budgetId) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}