package com.example.todo.presentor.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Task
import com.example.todo.data.database.TaskDatabase
import com.example.todo.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel (application: Application) : AndroidViewModel(application) {

    val allTasks : LiveData<List<Task>>
    val repository : TaskRepository

    init {
        val dao = TaskDatabase.getDatabase(application).getTaskDao()
        repository = TaskRepository(dao)
        allTasks = repository.allTask
    }

    fun deleteTask (task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun addTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }
}