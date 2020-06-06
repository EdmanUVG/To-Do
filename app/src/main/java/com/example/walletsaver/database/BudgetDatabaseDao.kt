package com.example.walletsaver.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDatabaseDao {

    @Insert
    fun insert(budget: Budget)

    @Update
    fun update(budget: Budget)

    @Delete
    fun delete(budget: Budget)

    @Query("SELECT * FROM budget_table WHERE id = :key")
    fun getBudget(key: Long): LiveData<Budget>

    @Query("SELECT * FROM budget_table ORDER BY id DESC")
    fun getBudgets(): LiveData<List<Budget>>
}