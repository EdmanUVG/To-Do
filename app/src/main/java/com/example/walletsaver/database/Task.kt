package com.example.walletsaver.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var budgetId: Long = 0L,

    val task: String,

    var priority: String,

    var tag: String,

    val dueDate: String,

    val iconIndex: Int,

    val creationDate: String

)