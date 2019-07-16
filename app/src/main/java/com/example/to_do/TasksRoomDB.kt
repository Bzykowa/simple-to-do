package com.example.to_do

import androidx.room.Database
import androidx.room.RoomDatabase

//object that refers to room database
@Database(entities = arrayOf(Tasks::class), version = 1)
abstract class TasksRoomDB : RoomDatabase() {

    abstract fun tasksDao(): TasksDao

}