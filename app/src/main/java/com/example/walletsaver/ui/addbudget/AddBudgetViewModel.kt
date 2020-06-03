package com.example.walletsaver.ui.addbudget

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Budget
import com.example.walletsaver.database.BudgetDatabaseDao
import kotlinx.coroutines.*
import java.io.IOError
import java.io.IOException
import kotlin.math.log

class AddBudgetViewModel(val database: BudgetDatabaseDao) : ViewModel() {

    val presupuesto = MutableLiveData<String>()


    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun insertBudget() {
        uiScope.launch {
            insert()
        }
    }

    private suspend fun insert() {
        withContext(Dispatchers.IO) {
            database.insert(Budget(budgets = presupuesto.value ?: ""))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
