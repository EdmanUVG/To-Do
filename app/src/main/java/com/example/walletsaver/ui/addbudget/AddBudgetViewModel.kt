package com.example.walletsaver.ui.addbudget

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Budget
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*
import java.sql.Date
import java.time.*
import java.util.*

class AddBudgetViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val amount = MutableLiveData<String>()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertBudget(category: String, iconIndex: Int) {
        uiScope.launch {
            insert(category, iconIndex)
        }
    }

    private suspend fun insert(category: String, iconIndex: Int) {
        withContext(Dispatchers.IO) {
            database.insert(Budget(amount = amount.value ?: "", category = category?:"", iconIndex = iconIndex?: 2))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
