package com.example.walletsaver.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "budget_table")
data class Task (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var budgetId: Long = 0L,

    val tag: String,

    val task: String,

    val income: Int,

    val expense: Int,

    val iconIndex: Int

)