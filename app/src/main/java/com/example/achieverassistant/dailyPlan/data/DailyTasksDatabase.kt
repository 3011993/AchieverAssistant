package com.example.achieverassistant.dailyPlan.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.achieverassistant.dailyPlan.DateConverters
import com.example.achieverassistant.dailyPlan.models.DailyTasks

@Database(entities = [DailyTasks::class], version = 1, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class DailyTasksDatabase : RoomDatabase() {

    abstract fun dailyDAO(): DailyDAO

}

