package com.example.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskTable")

// on below line we are specifying our column info
// and inside that we are passing our column name
class Task(
    @ColumnInfo(name = "title") var taskTitle: String,
    @ColumnInfo(name = "timestamp") var timeStamp: String,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean
) {
    // on below line we are specifying our key and
    // then auto generate as true and we are
    // specifying its initial value as 0
    @PrimaryKey(autoGenerate = true)
    var id = 0
}