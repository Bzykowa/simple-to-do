package com.example.to_do

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class that deals with currently displayed data while database stores data while app is off
class TaskListViewModel(application: Application) : AndroidViewModel(application){
    var tasks : LiveData<List<Tasks>>
    private val repository: TasksRepository
    val database : TasksRoomDB

    init {
        database = Room.databaseBuilder(
            application.applicationContext,
            TasksRoomDB::class.java,
            "tasks_database"
        ).build()
        val tasksDao = database.tasksDao()
        repository = TasksRepository(tasksDao)
        tasks = repository.allTasks
    }

    fun getList(): LiveData<List<Tasks>> {
        return tasks
    }

    //dispatching query execution to coroutines using repository object functions
    fun insert(task: Tasks) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun delete(task: Tasks) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun update(vararg tasks: Tasks) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(*tasks)
    }

    fun sort(opID : Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.sort(opID)
        tasks = repository.allTasks
    }

}