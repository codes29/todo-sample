package com.example.todo.data.repository

import androidx.lifecycle.LiveData
import com.example.todo.data.Task
import com.example.todo.data.database.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    val allTask: LiveData<List<Task>> = taskDao.getAllNotes()

    suspend fun insert(note: Task) {
        taskDao.insert(note)
    }

    suspend fun delete(note: Task){
        taskDao.delete(note)
    }

    suspend fun update(note: Task){
        taskDao.update(note)
    }
}