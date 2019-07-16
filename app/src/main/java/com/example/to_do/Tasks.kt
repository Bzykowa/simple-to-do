package com.example.to_do

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//room database entry
@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo(name = "description") val taskDescription: String,
    @ColumnInfo(name = "due_date") val taskDue: String,
    @ColumnInfo(name = "category") val taskCategory:  String,
    @ColumnInfo(name = "checked") var taskChecked: Boolean
)