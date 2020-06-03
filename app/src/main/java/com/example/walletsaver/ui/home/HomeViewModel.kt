package com.example.walletsaver.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.BudgetDatabaseDao

class HomeViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val budgets = database.getBudgets()

}