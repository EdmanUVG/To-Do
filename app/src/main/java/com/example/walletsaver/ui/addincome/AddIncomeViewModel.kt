package com.example.walletsaver.ui.addincome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class AddIncomeViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addIncome(income: Int, category: String) {
        uiScope.launch {
            update(income, category)
        }
    }

    private suspend fun update(income: Int, category: String) {
        withContext(Dispatchers.IO) {
            database.addIncome(income, category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
