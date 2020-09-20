package com.example.walletsaver.ui.home


import androidx.lifecycle.ViewModel
import com.example.walletsaver.database.Task
import com.example.walletsaver.database.TaskDatabaseDao
import kotlinx.coroutines.*


class BottomSheetAddTaskViewModel(val database: TaskDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertTask(task: String, priority: String, tag: String, dueDate: String, iconIndex: Int, creationDate: String ) {
        uiScope.launch {
            insert(task, priority, tag, dueDate, iconIndex, creationDate)
        }
    }

    private suspend fun insert(task: String, priority: String, tag: String, dueDate: String, iconIndex: Int, creationDate: String) {
        withContext(Dispatchers.IO) {
            database.insert(
                Task(task = task?: "", priority = priority ?: "", tag = tag ?: "", dueDate = dueDate ?: "",
                    iconIndex = iconIndex?: 2, creationDate = creationDate ?: "")
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}