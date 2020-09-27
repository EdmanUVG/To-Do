package com.example.walletsaver.database

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var taskId: Long = 0L,

    var task: String,

    var priority: String,

    var tag: String,

    var dueDate: String,

    var iconIndex: Int,

    var creationDate: String,

    @Nullable
    var subtask: String,

    @Nullable
    var description: String,

    var status: Int

)