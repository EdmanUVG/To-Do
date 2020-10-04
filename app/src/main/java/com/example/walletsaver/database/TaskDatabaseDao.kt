package com.example.walletsaver.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table WHERE id = :key")
    fun getTask(key: Long): LiveData<Task>

    @Query("SELECT COUNT(id) FROM task_table")
    fun getRowsCount(): LiveData<Int>

    @Query("SELECT SUM(task) FROM task_table")
    fun getSumOfBudgets(): LiveData<Int>

    @Query("SELECT * FROM task_table WHERE status = 0 ORDER BY id DESC")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status = 1 ORDER BY id DESC")
    fun getTaskCompleted(): LiveData<List<Task>>

    @Query("SELECT COUNT(status) FROM task_table WHERE status = 1")
    fun getCountCompletedTasks(): LiveData<Int>

    @Query("SELECT * FROM task_table WHERE status = 0 ORDER BY iconIndex ASC")
    fun getTaskByPriority(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status = 0 ORDER BY creationDate ASC")
    fun getTaskByDate(): LiveData<List<Task>>
}