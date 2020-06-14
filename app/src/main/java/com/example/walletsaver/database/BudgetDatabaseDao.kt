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
    fun updateBudget(expense: Int, category: String)

    @Query("UPDATE budget_table SET income = :income WHERE category = :category")
    fun addIncome(income: Int, category: String)

    @Delete
    fun delete(budget: Budget)

    @Query("SELECT * FROM budget_table WHERE id = :key")
    fun getBudget(key: Long): LiveData<Budget>

    @Query("SELECT COUNT(id) FROM budget_table")
    fun getRowsCount(): LiveData<Int>

    @Query("SELECT SUM(income) FROM budget_table")
    fun getSumOfIncomes(): LiveData<Int>

    @Query("SELECT SUM(expense) FROM budget_table")
    fun getSumOfExpenses(): LiveData<Int>

    @Query("SELECT SUM(budget) FROM budget_table")
    fun getSumOfBudgets(): LiveData<Int>


    @Query("SELECT * FROM budget_table ORDER BY id DESC")
    fun getBudgets(): LiveData<List<Budget>>
}