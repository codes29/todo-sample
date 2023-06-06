package com.example.todo.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.data.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note :Task)

    @Delete
    suspend fun delete(note: Task)

     @Query("Select * from taskTable order by id ASC")
    fun getAllNotes(): LiveData<List<Task>>

    @Update
    suspend fun update(note: Task)
}