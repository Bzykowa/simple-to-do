package com.example.to_do

import androidx.lifecycle.LiveData
import androidx.room.*

//linking queries in room database to functions
@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): LiveData<List<Tasks>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Tasks)

    @Delete
    suspend fun delete(task: Tasks)

    @Query("SELECT * FROM tasks WHERE description LIKE :desc LIMIT 1")
    fun findByDesc(desc: String): Tasks

    @Update
    suspend fun update(vararg tasks: Tasks)

    @Query("SELECT * FROM tasks ORDER BY due_date ASC")
    fun sortDate() : LiveData<List<Tasks>>

    @Query("SELECT * FROM tasks ORDER BY description ASC")
    fun sortDesc() : LiveData<List<Tasks>>

    @Query("SELECT * FROM tasks ORDER BY category ASC")
    fun sortCategory() : LiveData<List<Tasks>>
}