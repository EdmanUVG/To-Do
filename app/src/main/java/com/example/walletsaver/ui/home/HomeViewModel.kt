package com.example.walletsaver.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.BudgetDatabaseDao

class HomeViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val budgets = database.getBudgets()


    private val _budgetClicked = MutableLiveData<Long>()
    val budgetClicked: LiveData<Long>
    get() = _budgetClicked


    fun onBudgetClicked(budgetId: Long) {
        _budgetClicked.value = budgetId
    }

    fun onBudgetClickedCompleted() {
        _budgetClicked.value = null
    }
}