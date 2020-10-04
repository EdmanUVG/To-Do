package com.example.walletsaver.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_table")
data class Project (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var projectId: Long = 0L
)