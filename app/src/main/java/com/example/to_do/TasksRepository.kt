package com.example.to_do

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

//class responsible for getting data from/to database using coroutines to optimize operations (every query is done on other "thread")
class TasksRepository(private val tasksDao: TasksDao) {
    var allTasks: LiveData<List<Tasks>> = tasksDao.getAll()

    @WorkerThread
    suspend fun insert(task: Tasks) {
        tasksDao.insert(task)
    }

    @WorkerThread
    suspend fun delete(task: Tasks) {
        tasksDao.delete(task)
    }

    @WorkerThread
    suspend fun update(vararg tasks: Tasks) {
        tasksDao.update(*tasks)
    }

    @WorkerThread
    fun sort(opID : Int) {
        when(opID){
            0 -> allTasks = tasksDao.sortDesc()
            1 -> allTasks = tasksDao.sortDate()
            2 -> allTasks = tasksDao.sortCategory()
        }
    }
}