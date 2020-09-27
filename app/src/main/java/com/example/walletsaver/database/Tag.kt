package com.example.walletsaver.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table")
data class Tag (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var tagId: Long = 0L,

    var tag: String,

    var color: String
)