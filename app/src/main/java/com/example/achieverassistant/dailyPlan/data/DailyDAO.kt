package com.example.achieverassistant.dailyPlan.data

import androidx.room.*
import com.example.achieverassistant.dailyPlan.models.DailyTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDAO {
    @Insert
    fun insert(dailyTasks: DailyTasks)

    @Update
    fun update(dailyTasks: DailyTasks)

    @Delete
    fun delete(dailyTasks: DailyTasks)


    @Query("Delete FROM DailyTasks")
    fun deleteAll()

    @Query("SELECT * FROM DailyTasks")
    fun getAllTasks(): Flow<List<DailyTasks>>
}