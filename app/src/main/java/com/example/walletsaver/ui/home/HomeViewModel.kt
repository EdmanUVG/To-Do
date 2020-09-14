package com.example.walletsaver.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*

class HomeViewModel(val databaseUser: TaskDatabaseDao) : ViewModel() {

    val budgets = databaseUser.getBudgets()

    val rowsCount = databaseUser.getRowsCount()

    val sumOfIncomes = databaseUser.getSumOfIncomes()

    val sumOfExpenses = databaseUser.getSumOfExpenses()

    val sumOfBudgets = databaseUser.getSumOfBudgets()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _budgetClicked = MutableLiveData<Long>()
    val budgetClicked: LiveData<Long>
    get() = _budgetClicked


    fun onBudgetClicked(budgetId: Long) {
        _budgetClicked.value = budgetId
    }

    fun onBudgetClickedCompleted() {
        _budgetClicked.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}