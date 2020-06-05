package com.example.walletsaver.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BudgetDatabaseDao {

    @Insert
    fun insert(budget: Budget)

    @Delete
    fun delete(budget: Budget)

    @Query("SELECT * FROM budget_table WHERE id = :key")
    fun getBudget(key: Long): LiveData<Budget>

    @Query("SELECT * FROM budget_table ORDER BY id DESC")
    fun getBudgets(): LiveData<List<Budget>>
}