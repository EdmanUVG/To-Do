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

    // New Code here

    @Query("UPDATE budget_table SET expense = :expense WHERE tag = :category")
    fun updateBudget(expense: Int, category: String)

    @Query("UPDATE budget_table SET income = :income WHERE tag = :category")
    fun addIncome(income: Int, category: String)



    @Query("SELECT * FROM budget_table WHERE id = :key")
    fun getBudget(key: Long): LiveData<Task>

    @Query("SELECT COUNT(id) FROM budget_table")
    fun getRowsCount(): LiveData<Int>

    @Query("SELECT SUM(income) FROM budget_table")
    fun getSumOfIncomes(): LiveData<Int>

    @Query("SELECT SUM(expense) FROM budget_table")
    fun getSumOfExpenses(): LiveData<Int>

    @Query("SELECT SUM(task) FROM budget_table")
    fun getSumOfBudgets(): LiveData<Int>


    @Query("SELECT * FROM budget_table ORDER BY id DESC")
    fun getBudgets(): LiveData<List<Task>>
}