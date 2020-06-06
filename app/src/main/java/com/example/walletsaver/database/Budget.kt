package com.example.walletsaver.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "budget_table")
data class Budget (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var budgetId: Long = 0L,

    var amount: String,

    val category: String,

    @Nullable
    val iconIndex: Int
)