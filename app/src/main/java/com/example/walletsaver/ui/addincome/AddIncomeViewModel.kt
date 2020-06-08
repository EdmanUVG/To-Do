package com.example.walletsaver.ui.addincome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*

class AddIncomeViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val income = MutableLiveData<String>()

    val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addIncome(category: String) {
        uiScope.launch {
            income.value?.let { update(it, category) }
        }
    }

    private suspend fun update(income: String, category: String) {
        withContext(Dispatchers.IO) {
            database.addIncome(income, category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}
