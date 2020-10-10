package com.example.walletsaver.database

import kotlin.properties.Delegates

class SubTask() {

    var id: Long = -1
    var subTaskId = -1

    lateinit var task: String

    lateinit var priority: String

    lateinit var tag: String

    lateinit var dueDate: String

    var iconIndex : Int = 0
    
    lateinit var creationDate: String

    var status: Int = 0

}