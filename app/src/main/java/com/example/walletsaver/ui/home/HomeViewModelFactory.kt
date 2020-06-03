package com.example.walletsaver.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.database.BudgetDatabaseDao
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val database: BudgetDatabaseDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}