package com.example.walletsaver.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDatabaseDao {

    @Insert
    fun insert(budget: Budget)

    @Update
    fun update(budget: Budget)

    @Query("UPDATE budget_table SET expense = :expense WHERE category = :category")
    fun updateBudget(expense: String, category: String)

    @Query("UPDATE budget_table SET income = :income WHERE category = :category")
    fun addIncome(income: String, category: String)

    @Delete
    fun delete(budget: Budget)

    @Query("SELECT * FROM budget_table WHERE id = :key")
    fun getBudget(key: Long): LiveData<Budget>

    @Query("SELECT income FROM budget_table")
    fun getIncomes(): LiveData<String>

    @Query("SELECT amount FROM budget_table")
    fun getSumOfBudgets(): LiveData<List<String>>

    @Query("SELECT COUNT(id) FROM budget_table")
    fun getRowsCount(): LiveData<Int>


    @Query("SELECT * FROM budget_table ORDER BY id DESC")
    fun getBudgets(): LiveData<List<Budget>>
}