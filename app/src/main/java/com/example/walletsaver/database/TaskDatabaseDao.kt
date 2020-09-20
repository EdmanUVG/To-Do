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
    fun getBudget(key: Long): LiveData<Task>

    @Query("SELECT COUNT(id) FROM task_table")
    fun getRowsCount(): LiveData<Int>

    @Query("SELECT SUM(task) FROM task_table")
    fun getSumOfBudgets(): LiveData<Int>

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY iconIndex ASC")
    fun getTasksByPriority(): LiveData<List<Task>>
}