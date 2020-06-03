package com.example.walletsaver.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class Budget (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var budgetId: Long = 0L,

    var budgets: String
)